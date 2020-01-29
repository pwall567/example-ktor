package com.example.archunit

import kotlin.test.Test

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.GeneralCodingRules

class ArchUnitTests {

    private val importedClasses: JavaClasses = ClassFileImporter().importPath("build/classes/kotlin/main")

    @Test fun `should not allow external references in ports`() {
        val rule: ArchRule = noClasses().that().resideInAPackage("$packageBase.ports..").
                should().dependOnClassesThat().
                resideOutsideOfPackages("$packageBase.ports..", "kotlin..", "java..", "org.jetbrains.annotations..")
        rule.check(importedClasses)
    }

    @Test fun `should not allow external references in application`() {
        val rule: ArchRule = noClasses().that().resideInAPackage("$packageBase.application..").
                should().dependOnClassesThat().
                resideOutsideOfPackages("$packageBase.application..", "$packageBase.ports..", "kotlin..", "java..",
                        "org.jetbrains.annotations..")
        rule.check(importedClasses)
    }

    @Test fun `should not allow access to application other than from adapters`() {
        val rule: ArchRule = classes().that().resideInAPackage("$packageBase.application..").
                should().onlyBeAccessed().
                byAnyPackage("$packageBase.adapters..", "$packageBase.application..", packageBase)
        rule.check(importedClasses)
    }

    @Test fun `should not access joda time`() {
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME.check(importedClasses)
    }

    @Test fun `should not access standard streams`() {
        GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(importedClasses)
    }

    @Test fun `should not allow generic exceptions`() {
        GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(importedClasses)
    }

    @Test fun `should not use java logging`() {
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(importedClasses)
    }

    companion object {
        const val packageBase = "com.example"
    }

}
