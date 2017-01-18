package com.budget.testutil;

public class ThrowableCaptor {
//https://objectpartners.com/2013/09/18/the-benefits-of-using-assertthat-over-other-assert-methods-in-unit-tests/
//http://www.codeaffine.com/2014/07/28/clean-junit-throwable-tests-with-java-8-lambdas/
//http://blog.codeleak.pl/2014/07/junit-testing-exception-with-java-8-and-lambda-expressions.html
//http://joel-costigliola.github.io/assertj/	
	@FunctionalInterface
	public interface ExceptionThrower {
	    void throwException() throws Throwable;
	}
	
	public static Throwable captureThrowable(ExceptionThrower exceptionThrower) {
        try {
            exceptionThrower.throwException();
            return null;
        } catch (Throwable caught) {
            return caught;
        }
    }
}
