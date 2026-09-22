package com.crackersnacker;

import com.crackersnacker.entity.Script;

class BasicScript extends Script {

    public void foo() {
        BasicScript foo = this.getScript(BasicScript.class);
        foo.bar();
    }

    public void bar() {
        System.out.println("Bar has been barred");
    }
}
