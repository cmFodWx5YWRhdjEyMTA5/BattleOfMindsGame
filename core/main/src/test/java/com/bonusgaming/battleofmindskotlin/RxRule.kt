package com.bonusgaming.battleofmindskotlin

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object:Statement(){
            override fun evaluate() {
                RxJavaPlugins.
            }
        }
    }
}