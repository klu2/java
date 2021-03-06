package com.structurizr.analysis;

import com.structurizr.model.CodeElement;
import com.structurizr.model.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This strategy finds all referenced types in the same package as the component type,
 * and is useful if each component resides in its own Java package.
 */
public class ReferencedTypesInSamePackageSupportingTypesStrategy extends SupportingTypesStrategy {

    private boolean includeIndirectlyReferencedTypes;

    public ReferencedTypesInSamePackageSupportingTypesStrategy() {
        this(true);
    }

    public ReferencedTypesInSamePackageSupportingTypesStrategy(boolean includeIndirectlyReferencedTypes) {
        this.includeIndirectlyReferencedTypes = includeIndirectlyReferencedTypes;
    }

    @Override
    public Set<Class<?>> findSupportingTypes(Component component) {
        CodeElement codeElement = component.getType();
        if (codeElement != null) {
            ReferencedTypesSupportingTypesStrategy referencedTypesSupportingTypesStrategy = new ReferencedTypesSupportingTypesStrategy(includeIndirectlyReferencedTypes);
            referencedTypesSupportingTypesStrategy.setTypeRepository(getTypeRepository());
            Set<Class<?>> supportingTypes = referencedTypesSupportingTypesStrategy.findSupportingTypes(component);

            return supportingTypes.stream()
                    .filter(type -> type.getPackage().getName().startsWith(codeElement.getPackage()))
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

}
