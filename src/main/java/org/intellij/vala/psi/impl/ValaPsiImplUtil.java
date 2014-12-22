package org.intellij.vala.psi.impl;


import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.intellij.vala.psi.*;
import org.intellij.vala.resolve.ValaConstructorReference;
import org.intellij.vala.resolve.ValaTypeReference;
import org.intellij.vala.resolve.method.ValaMethodReference;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ValaPsiImplUtil {

    public static PsiReference getReference(ValaMethodCall methodCall) {
        return null;
    }

    public static PsiReference getReference(ValaSimpleName simpleName) {
        if (isPartOfMethodCall(simpleName)) {
            return new ValaMethodReference(simpleName);
        }
        return null;
    }

    private static boolean isPartOfMethodCall(ValaSimpleName simpleName) {
        ValaPrimaryExpression parent = (ValaPrimaryExpression) simpleName.getParent();
        return !parent.getMethodCallList().isEmpty();
    }

    public static PsiReference getReference(ValaTypeWeak typeWeak) {
        int nameLength = typeWeak.getSymbol().getText().length();
        return new ValaTypeReference(typeWeak, new TextRange(0, nameLength));
    }

    public static PsiReference getReference(ValaType type) {
        return null;
    }

    public static ValaNamespaceDeclaration getNamespace(ValaNamespaceMember valaClassDeclaration) {
        return PsiTreeUtil.getParentOfType(valaClassDeclaration, ValaNamespaceDeclaration.class, false);
    }

    public static String getName(ValaClassDeclaration classDeclaration) {
        return classDeclaration.getSymbol().getText();
    }

    public static String getName(ValaSymbolPart symbolPart) {
        return symbolPart.getIdentifier().getText();
    }

    public static String getName(ValaMemberPart memberPart) {
        return memberPart.getIdentifier().getText();
    }

    public static String getName(ValaSimpleName simpleName) {
        return simpleName.getIdentifier().getText();
    }

    public static String getName(ValaMethodDeclaration methodDeclaration) {
        return methodDeclaration.getIdentifier().getText();
    }

    public static PsiElement setName(ValaPsiElement valaPsiElement, String newName) {
        throw new IncorrectOperationException("changing name of this element is not supported");
    }

    public static PsiReference getReference(ValaObjectOrArrayCreationExpression objectCreationExpression) {
        return new ValaConstructorReference(objectCreationExpression);
    }

    public static PsiReference getReference(ValaMemberPart memberPart) {
        return null;
    }

    public static List<ValaMethodDeclaration> getMethodDeclarations(ValaClassDeclaration classDeclaration) {
        return ClassDeclarationUtil.getMethodDeclarations(classDeclaration);
    }

    public static List<ValaNamespaceMember> getNamespaceMemberList(ValaClassDeclaration classDeclaration) {
        return ClassDeclarationUtil.getNamespaceMemberList(classDeclaration);
    }
}