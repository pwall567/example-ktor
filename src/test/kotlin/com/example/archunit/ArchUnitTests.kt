package com.example.archunit

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import kotlin.test.Test

class ArchUnitTests {

    private val importedClasses: JavaClasses = ClassFileImporter().importPath("build/classes/kotlin/main")

    @Test fun `should not allow external references in ports`() {
        val rule: ArchRule = noClasses().that().resideInAPackage("..ports..").should().dependOnClassesThat().
                resideOutsideOfPackages("..ports..", "kotlin..", "java..", "org.jetbrains.annotations..")
        rule.check(importedClasses)
    }

    @Test fun `should not allow external references in application`() {
        val rule: ArchRule = noClasses().that().resideInAPackage("..application..").should().dependOnClassesThat().
                resideOutsideOfPackages("com.example..", "kotlin..", "java..", "org.jetbrains.annotations..")
        rule.check(importedClasses)
    }

}
