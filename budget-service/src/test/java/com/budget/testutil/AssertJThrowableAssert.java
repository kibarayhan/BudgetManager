//package com.budget.testutil;
//
//import org.assertj.core.api.Assertions;
//import org.assertj.core.api.ThrowableAssert;
//
//import com.budget.testutil.ThrowableCaptor.ExceptionThrower;
//
//public class AssertJThrowableAssert {
//	public static ThrowableAssert assertThrown(ExceptionThrower exceptionThrower) {
//		try {
//			exceptionThrower.throwException();
//		} catch (Throwable throwable) {
//			return (ThrowableAssert) Assertions.assertThat(throwable);
//		}
//		throw new ExceptionNotThrownAssertionError();
//	}
//}