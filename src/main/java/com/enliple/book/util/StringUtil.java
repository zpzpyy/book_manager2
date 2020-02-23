package com.enliple.book.util;

import java.util.Optional;

public class StringUtil {

	/**
	 * 객체 Null확인하고 Null이면 default값 리턴
	 *
	 * @param str
	 * @param defaultValue
	 * @return
	 *
	 * @author ljs
	 * @since 0.1
	 */
	public static Object customOptional( Object str, Object defaultValue ){
		Optional<Object> op = Optional.ofNullable( str );
		Optional<Object> dop = Optional.ofNullable( defaultValue );
		str = op.orElse( dop.isPresent() ? dop.get() : dop.orElseGet(()-> dop.get()) );
		return str;
	}

	public static String stringOptional( Object str ) {
		return String.valueOf(customOptional(str,""));
	}
	public static int integerOptional( Object str ) {
		return Integer.valueOf(customOptional( str , 0).toString()) ;
	}
	public static Long longOptional( Object str ) {
		return Long.valueOf(customOptional( str , 0).toString()) ;
	}

	
}
