package com.github.tomasmilata.intelliroutes;

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoutesCompletionContributor extends CompletionContributor {

    private static List<LookupElementBuilder> httpVerbs = Stream.of(
            "GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"
    ).map(LookupElementBuilder::create).collect(Collectors.toList());

    public RoutesCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.VERB).withLanguage(RoutesLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addAllElements(httpVerbs);
                    }
                }
        );
    }
}
