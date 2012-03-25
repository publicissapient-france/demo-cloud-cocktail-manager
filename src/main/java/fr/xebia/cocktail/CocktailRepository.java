/*
 * Copyright 2008-2012 Xebia and the original author or authors.
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
package fr.xebia.cocktail;

import java.util.Collection;
import java.util.List;

public interface CocktailRepository {

    void update(Cocktail cocktail);

    void insert(Cocktail cocktail);

    Cocktail get(String id);

    Collection<Cocktail> getAll();

    boolean delete(Cocktail cocktail);

    void purgeRepository();

    public List<String> autocompleteCocktailNameWords(String query);

}
