package com.jkb.junbin.sharing;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.jkb.junbin.sharing")
public class ArchRuleTest {
    private static final String BASE = "com.jkb.junbin.sharing.";
    // Layer
    private static final String FEATURE = BASE + "feature..";
    private static final String FUNCTION = BASE + "function..";
    private static final String LIBRARY = BASE + "library..";
    //BUSINESS
    private static final String FILE_BUNDLE = BASE + "feature.file..";
    private static final String MESSAGE_BUNDLE = BASE + "feature.message..";
    private static final String ACCOUNT_BUNDLE = BASE + "feature.account..";

    @ArchTest
    public static final ArchRule library_should_only_dependOn_itself =
            target_package_only_dependOn_itSelf(LIBRARY);

    @ArchTest
    public static final ArchRule function_should_not_dependOn_feature =
            target_package_not_dependOn_other_package(FUNCTION, FEATURE);

    @ArchTest
    public static final ArchRule file_bundle_should_not_dependOn_other_feature =
            target_package_not_dependOn_other_package(FILE_BUNDLE, MESSAGE_BUNDLE, ACCOUNT_BUNDLE);

    @ArchTest
    public static final ArchRule message_bundle_should_not_dependOn_other_bundle =
            target_package_not_dependOn_other_package(MESSAGE_BUNDLE, FILE_BUNDLE, ACCOUNT_BUNDLE);

    @ArchTest
    public static final ArchRule account_bundle_should_not_dependOn_other_bundle =
            target_package_not_dependOn_other_package(ACCOUNT_BUNDLE, FILE_BUNDLE, MESSAGE_BUNDLE);

    private static ClassesShouldConjunction target_package_not_dependOn_other_package(String targetPackage, String... otherPackages) {
        return noClasses().that().resideInAPackage(targetPackage)
                .should().dependOnClassesThat().resideInAnyPackage(otherPackages);
    }

    private static ClassesShouldConjunction target_package_only_dependOn_itSelf(String targetPackage) {
        return classes().that().resideInAPackage(targetPackage)
                .should().dependOnClassesThat().resideInAPackage(targetPackage);
    }
}