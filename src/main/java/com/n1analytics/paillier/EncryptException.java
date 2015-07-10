/**
 * Copyright 2015 NICTA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.n1analytics.paillier;

public class EncryptException extends PaillierRuntimeException {
	private static final long serialVersionUID = -5497548195268474994L;

	public EncryptException() { super(); }

	public EncryptException(String message) { super(message); }

	public EncryptException(Throwable cause) { super(cause); }

	public EncryptException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncryptException(
		String message,
		Throwable cause,
		boolean enableSuppression,
		boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
