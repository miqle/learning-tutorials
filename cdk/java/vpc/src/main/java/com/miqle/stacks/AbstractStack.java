package com.miqle.stacks;


import software.amazon.awscdk.core.Stack;

public abstract class AbstractStack {
    protected Stack stack;

    public Stack getStack() {
        return stack;
    }
}
