package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.search.searches.ClassInheritorsSearch
import com.intellij.util.ProcessingContext


class OtherRoutersCompletionContributor : CompletionContributor() {
    init {
        val elementPattern = PlatformPatterns.psiElement(RoutesTypes.ROUTER_REFERENCE)
                .withLanguage(RoutesLanguage.INSTANCE)
        extend(CompletionType.BASIC, elementPattern, otherRoutersCompletionProvider)
    }

    companion object {
        private val otherRoutersCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project

                        // routes files suggestions
                        ProjectFileIndex.routesFiles(project).forEach {
                            val suggestion = LookupElementBuilder
                                    .create(generatedRouterClassName(it))
                                    .bold()
                                    .withIcon(it.getIcon(0))
                                    .appendTailText(" (from file ${it.name})", true)
                            resultSet.addElement(suggestion)
                        }

                        // Router classes suggestion
                        val projectWithLibrariesScope = ProjectScope.getAllScope(project)
                        playRouterType(project, projectWithLibrariesScope)?.let { routerType ->
                            val query = ClassInheritorsSearch.search(routerType)
                            query.findAll()
                                    .forEach {
                                        it.qualifiedName?.let { className ->
                                            val suggestion = LookupElementBuilder
                                                    .create(className)
                                                    .bold()
                                                    .withIcon(it.getIcon(0))
                                            resultSet.addElement(suggestion)
                                        }

                                    }

                        }


                    }
                }

        private fun generatedRouterClassName(file: PsiFile) = when (file.name) {
            "routes" -> "router.Routes"
            else -> file.name.replace("\\.routes$".toRegex(), ".Routes")
        }

        private fun playRouterType(project: Project, scope: GlobalSearchScope): PsiClass? {
            val psiFacade = JavaPsiFacade.getInstance(project)
            return psiFacade.findClass("play.api.routing.Router", scope)
        }
    }
}
