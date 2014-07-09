/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grails.gradle.plugin.tasks

import org.gradle.api.internal.ConventionTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.grails.gradle.plugin.eclipse.GrailsEclipseConfigurator

/**
 * Created by Jeevanandam M. (jeeva@myjeeva.com) on 7/8/14.
 */
class GrailsEclipseJdtGroovyTask extends ConventionTask {
    @Input
    def groovyVersion

    @OutputFile
    File outputFile

    GrailsEclipseJdtGroovyTask() {
        super()
        description = 'Generates the Eclipse JDT Groovy settings file.'

        project.tasks.findByName(EclipsePlugin.ECLIPSE_TASK_NAME)?.dependsOn(GrailsEclipseConfigurator.ECLIPSE_JDT_GROOVY_TASK_NAME)
    }

    @TaskAction
    def createJdtGroovyPrefs() {
        def groovyCompilerVersion = groovyVersion[0..2].replaceAll(/\./, "")

        def file = getOutputFile()
        if (!file.isFile()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        def props = new Properties()
        props.load(file.newDataInputStream())
        props.setProperty('eclipse.preferences.version', '1')
        props.setProperty('groovy.compiler.level', groovyCompilerVersion)
        props.store(file.newWriter(), null)
    }
}
