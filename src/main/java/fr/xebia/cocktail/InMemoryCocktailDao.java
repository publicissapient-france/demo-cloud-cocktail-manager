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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;

public class InMemoryCocktailDao implements CocktailRepository {

    private Map<String, Cocktail> cocktails = new MapMaker().concurrencyLevel(1).makeMap();

    public InMemoryCocktailDao() {
        Cocktail sexOnTheBeach = new Cocktail()
                .withObjectId(ObjectId.get())
                .withName("Sex On The Beach")
                .withIngredient("1 shot", "vodka")
                .withIngredient("1 shot", "peach schnapps (archers)")
                .withIngredient("200 ml", "orange juice")
                .withIngredient("200 ml", "cranberry juice")
                .withIngredient("2 shots", "raspberry syrup")
                .withInstructions(
                        "Add ice to glass pour in shot of vodka add peach shnapps mix with orange, cranberry and raspberry\n" //
                                + "\n" //
                                + "Serve with an ubrella and a mixer stick and a fancy straw and an orange slice on side of "
                                + "glass this one is gorgeous can't believe you don't already have it on here!");

        update(sexOnTheBeach);

        Cocktail longIslandIcedTea = new Cocktail()
                .withObjectId(ObjectId.get())
                .withName("Long Island Iced tea")
                .withIngredient("1 Measure", "vodka")
                .withIngredient("1 Measure", "gin")
                .withIngredient("1 Measure", "white rum")
                .withIngredient("1 Measure", "tequila")
                .withIngredient("1 Measure", "triple sec")
                .withIngredient("3 measures", "orange juice")
                .withIngredient("to topp up the glass", "coke")
                .withInstructions(
                        "In a tall glass , add ice and all the ingredients and stir well. It should have the appearance of cloudy tea. Top with a piece of lemon\n"
                                + "\n"
                                + "Very yummy & very very decieving. It will get you hammered after only about 2 so drink with caution");

        ;
        update(longIslandIcedTea);
    }

    @Override
    public boolean delete(Cocktail cocktail) {
        return cocktails.remove(cocktail).getId() != null;
    }
    
    
    @Override
    public void update(Cocktail cocktail) {
        cocktails.put(cocktail.getId(), cocktail);
    }

    @Override
    public void insert(Cocktail cocktail) {
        Preconditions.checkArgument(cocktail.getId() == null, "Given id must be null in %s", cocktail);
        cocktail.setObjectId(ObjectId.get());
        cocktails.put(cocktail.getId(), cocktail);
    }

    @Override
    public Cocktail get(String id) {
        return cocktails.get(id);
    }

    @Override
    public Collection<Cocktail> getAll() {
        return this.cocktails.values();
    }
    @Override
    public void purgeRepository() {
        this.cocktails.clear();
        
    }
    
    @Override
    public List<String> autocompleteCocktailNameWords(String query) {
        // TODO
        return Collections.emptyList();
    }
}
