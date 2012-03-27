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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CocktailRepositoryInitializer {

    private final static Logger logger = LoggerFactory.getLogger(CocktailRepositoryInitializer.class);

    public static void main(String[] args) throws Exception {
        try {
            new CocktailRepositoryInitializer().initializeRepository();
        } catch (Exception e) {
            logger.error("Exception initializing the repository", e);
            throw e;
        }
    }

    public void initializeRepository() throws Exception {

        CocktailRepository cocktailRepository = new CocktailRepository( //
                "mongodb://localhost:27017/devoxxfr-demo", //
                "http://localhost:8983/solr/");
        cocktailRepository.purgeRepository();

        cocktailRepository.insert(buildLongIslandCocktail());
        cocktailRepository.insert(buildSexOnTheBeachCocktail());
    }

    protected Cocktail buildSexOnTheBeachCocktail() {
        Cocktail sexOnTheBeach = new Cocktail()
                .withName("Sex On The Beach")
                .withIngredient("1 shot", "vodka")
                .withIngredient("1 shot", "peach schnapps (archers)")
                .withIngredient("200 ml", "orange juice")
                .withIngredient("200 ml", "cranberry juice")
                .withIngredient("2 shots", "raspberry syrup")
                .withPhotoUrl("http://xebia-cocktail.s3-website-us-east-1.amazonaws.com/4703755392347885371.jpg")
                .withSourceUrl("http://www.cocktailmaking.co.uk/displaycocktail.php/321-Sex-On-The-Beach")
                .withInstructions(
                        "Add ice to glass pour in shot of vodka add peach shnapps mix with orange, cranberry and raspberry\n" //
                                + "\n" //
                                + "Serve with an ubrella and a mixer stick and a fancy straw and an orange slice on side of "
                                + "glass this one is gorgeous can't believe you don't already have it on here!");
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
                .withPhotoUrl("http://xebia-cocktail.s3-website-us-east-1.amazonaws.com/6762530443361434570.jpg")
                .withSourceUrl("http://www.cocktailmaking.co.uk/displaycocktail.php/1069-Long-Island-Iced-tea")
                .withInstructions(
                        "In a tall glass , add ice and all the ingredients and stir well. It should have the appearance of cloudy tea. Top with a piece of lemon\n"
                                + "\n"
                                + "Very yummy & very very decieving. It will get you hammered after only about 2 so drink with caution");
        return longIslandIcedTea;
    }
}
