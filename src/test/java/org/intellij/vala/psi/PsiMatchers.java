package org.intellij.vala.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.intellij.vala.reference.ResolveClassTest;

import static com.intellij.psi.util.PsiTreeUtil.getParentOfType;

public final class PsiMatchers {

    public static Matcher<PsiElement> hasParentOfType(final Class<? extends PsiElement> expectedPsiElement) {
        return new CustomTypeSafeMatcher<PsiElement>("has parent of type " + expectedPsiElement) {
            @Override
            protected boolean matchesSafely(PsiElement psiElement) {
                return PsiTreeUtil.getParentOfType(psiElement, expectedPsiElement, ResolveClassTest.NOT_STRICT) != null;
            }
        };
    }

    private static Matcher<PsiElement> hasRootThat(final Matcher<? extends PsiElement> rootMatcher) {
        return new CustomTypeSafeMatcher<PsiElement>("has root that " + rootMatcher.toString()) {
            @Override
            protected boolean matchesSafely(PsiElement psiElement) {
                return rootMatcher.matches(getRoot(psiElement));
            }
        };
    }

    public static Matcher<PsiElement> hasNoErrors() {
        return new CustomTypeSafeMatcher<PsiElement>("has no errors") {

            @Override
            protected boolean matchesSafely(PsiElement element) {
                return !PsiTreeUtil.hasErrorElements(element);
            }
        };
    }

    private static PsiElement getRoot(PsiElement element) {
        while (element.getParent() != null) {
            element = element.getParent();
        }
        return element;
    }

    public static Matcher<PsiElement> isInFile(final Matcher<? super PsiFile> psiFileMatcher) {
        return new CustomTypeSafeMatcher<PsiElement>("is contained in file that " + psiFileMatcher) {

            @Override
            protected boolean matchesSafely(PsiElement o) {
                PsiFile file = getParentOfType(o, PsiFile.class);
                return psiFileMatcher.matches(file);
            }
        };
    }

    public static Matcher<? super PsiElement> hasName(final Matcher<String> name) {
        return new CustomTypeSafeMatcher<PsiElement>("file with name " + name) {
            @Override
            protected boolean matchesSafely(PsiElement psiFile) {
                return psiFile instanceof PsiNamedElement && name.matches(((PsiNamedElement) psiFile).getName());
            }
        };
    }


}