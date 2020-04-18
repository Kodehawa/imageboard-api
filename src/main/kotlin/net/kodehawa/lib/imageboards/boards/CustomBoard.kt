/*
 * Copyright 2017 Kodehawa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kodehawa.lib.imageboards.boards

/**
 * Custom board information.
 *
 * @author Avarel
 */
class CustomBoard(
		override val scheme: String,
		override val host: String,
		override val path: String,
		private val separator: String,
		override val pageMarker: String
) : Board {

	override val query: String?
		get() = separator

}