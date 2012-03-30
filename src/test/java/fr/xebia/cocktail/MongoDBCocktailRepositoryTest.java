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

import static org.junit.Assert.*;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.DBObject;

/**
 * 
 * @author cyrilleleclerc
 * 
 */
public class MongoDBCocktailRepositoryTest {

    static private CocktailRepository cocktailRepository;

    @BeforeClass
    public static void before() throws Exception {
        cocktailRepository = new CocktailRepository("mongodb://localhost:27017/devoxxfr-demo", "http://localhost:8983/solr/");
    }

    @AfterClass
    public static void afterClass() {
        cocktailRepository.preDestroy();
    }

    @Ignore
    @Test
    public void testInsertUpdateAndDelete() {
        Cocktail sexOnTheBeach = buildLongIslandCocktail();

        cocktailRepository.insert(sexOnTheBeach);
        assertNotNull("objectId has not been generated", sexOnTheBeach.getObjectId());

        // boolean deleted = cocktailDao.delete(sexOnTheBeach);
        // assertTrue("cocktail " + sexOnTheBeach + " was not deleted", deleted);
    }

    @Test
    public void testBsonMapping() {

        Cocktail sexOnTheBeach = buildSexOnTheBeachCocktail();
        ObjectId objectId = ObjectId.get();
        sexOnTheBeach.setObjectId(objectId);

        DBObject sexOnTheBeachAsDbObject = cocktailRepository.toBson(sexOnTheBeach);

        Cocktail actual = cocktailRepository.fromBson(sexOnTheBeachAsDbObject);

        assertEquals(objectId, actual.getObjectId());
        assertEquals("Sex On The Beach", actual.getName());
        assertEquals(5, actual.getIngredients().size());
        assertArrayEquals(new String[] { "vodka", "peach schnapps (archers)", "orange juice", "cranberry juice", "raspberry syrup" },
                actual.getIngredientNames().toArray());
        assertEquals(2, actual.getComments().size());
    }

    protected Cocktail buildSexOnTheBeachCocktail() {
        Cocktail sexOnTheBeach = new Cocktail()
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
                                + "glass this one is gorgeous can't believe you don't already have it on here!") //
                .withComment("I like it!!") //
                .withComment("Perfect balance between orange and vodka.");
        return sexOnTheBeach;
    }

    protected Cocktail buildLongIslandCocktail() {
        Cocktail longIslandIcedTea = new Cocktail()
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
                                + "Very yummy & very very decieving. It will get you hammered after only about 2 so drink with caution") //
                .withComment("Too strong for me!!");
        return longIslandIcedTea;
    }

    @Ignore
    @Test
    public void testAutoComplete() {
        Collection<String> words = cocktailRepository.suggestCocktailNameWords("lon");
        assertArrayEquals(new String[] { "long" }, words.toArray());
    }
}
