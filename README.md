# 2022-241

**Topic**-Health App: Food Allergy Prevention, Non-communicable Diseases Prediction, and Calories Tracker

**Main objective**- Implement an efficient methodology to detect and predict food allergy and the allergens caused the allergy, identify ingredients and deliver the recipes 
and food items that do not have those specific allergens, Predict non-communicable diseases using the user's daily meal plans and recommending health tips to avoid those diseases, 
and to Keep tracking of Food intake, Nutrients, and calories in the food. 

**Main Research questions**- 1.How can we protect ourselves from food allergic reactions?
                            2. Do people know about the possible symptoms of a food allergy?
                            3. Do they follow a proper meal plan to track daily calorie intake?
                            4. Are they aware about the non-communicable diseases that they would get from not following a proper meal plan?
                            5. How are we going to find the optimum solution for the above prioritized questions?
                            
**Individual Components**- *Member 1: IT19213972_Nawarathne I.K.S.S*
                            PREDICTION OF THE USER’S ALLERGY AND ALLERGENS THAT CAUSED THE ALLERGY BY ANALYZING THE SYMPTOMS AND FOOD ITEMS ENTERED BY THE USER
                            
**Individual research question**- *Member 1: IT19213972_Nawarathne I.K.S.S*
                                Most people have no idea whether they have allergies to what they eat and what side effects they would get due to consuming a portion of food.
                                With the development of technology, there is vital room for innovative software pieces in human lives to predict the allergens that caused the 
                                allergy once diagnosed with the allergic reaction. Traditional laboratory-based detection technologies and historical records tracking are often 
                                unreliable for consumers due to the requirement of a scientific background. As a result, developing a convenient and straightforward solution linked 
                                to human lives as detectors to improve user accessibility is critical. Thus, we are trying to identify the various input parameters and algorithms 
                                that need to be understood to derive a methodology that will help predict allergens that caused the allergy and accurately give a prediction to the 
                                users. 
                                

**Individual Objectives**- *Member 1: IT19213972_Nawarathne I.K.S.S*
                            Even though there have been diverse approaches,from choosing the accurate algorithm to develop the functionality discussed above, there has been 
                            no significant research in using Deep learning methodologies for Allergy prediction in Sri Lanka. Thus, the main objective of this study is 
                            to implement an efficient methodology to detect and predict food allergy and the allergens caused the allergy by analyzing the food items consumed, 
                            tracking prevailing signs and symptoms entered by the user within 12 hours, for three weeks. 
                            The functionality is ideal for those unaware of their allergies and allergens. 
                            
                            **Sub Objectives:** 
                            1. Elimination of Allergens - The system shall predict the food item/allergens cause the allergy by analyzing the entered data for three weeks. 
                            It is likely to be affected if frequently consumed food items are within the 'big eight'; the eight food groups responsible for 90% of food allergy 
                            reactions. Once the food item is predicted, it can be eliminated. This elimination of Allergens involves removing a particular food from the user's 
                            diet for two weeks. That way, the users know that the food eliminated was causing the allergies and can be refrained. The app would aid in removing 
                            food allergies with the help of analyzing and predicting techniques. 
                            
                            2. Symptom Tracking - Analyze the symptoms entered by the user and predict the allergy the user has concerning the food item or the allergens that 
                            caused the allergy. The user can select their prevailing symptoms from a list of medical symptoms provided through the app (medical symptoms will be 
                            filtered concerning the food items entered into the system) and record them as part of the prediction. Users can record the details as needed in 
                            the symptoms section of check-in for three weeks. The above symptom tracking feature would help predict the user's allergy. 
                            
                            3. Informed decision making - The user can become an active participant in any Allergy-healthcare decision, feeling empowered to discuss procedures, 
                            self-diagnoses, and treatments with various healthcare specialists as the app provides 95% accurate medical decisions.
                            
                            4. Generation of PDF reports - The reports may contain information such as the system predicted food item/allergens, symptoms entered by the user, 
                            any other relevant symptoms (detected by the application itself), the system predicted allergy, quick remedies to be taken, and outcomes. 
                            These reports can help users discover triggers or help them stay on track to minimize symptoms based on the allergy & allergens. 

/**
*
*/

**Individual Components**- *Member 2: IT19111070_Ahamed M.F.A*
                            PREDICTION OF THE ALLERGY FREE RECIPE AND FOOD RESTRICTION ALTERNATIVES 
                            
**Individual research question**- *Member 2: IT19111070_Ahamed M.F.A*
                                Food allergies affect up to 4% of adults and up to 8% of children under the age of five.While there is no treatment for food allergies,
                                some children outgrow them as they grow older. Most of them avoided the food they desired since their childhood.Most of the research solely 
                                look at allergy-free components and they were used in various applications. They were unable to cover this region as it has a variety of people from 
                                different ethnicities, religions, and communities. Food is a sensitive place where everyone is more concern about.Using machine learning we are predicting 
                                the recipes for the user by getting user's allergen taking through the variuos algorithms accurately

                                

**Individual Objectives**- *Member 2: IT19111070_Ahamed M.F.A*
                            Establish an Allergy free food app that Predict Recipes which does not contain Allergens and have functions such as searching Allergens and finding Recipes of Food Items,
                            share known Recipe to others in Forum, Specify the Recipe by Food preference such as Halal, Kosher and Vegan
                            
                            **Sub Objectives:** 
                            1.Create a Forum to active discussion - The purpose of this platform is to engage people around the world to engage and ask doubts and questions of food
                            allergy and post their recipes which does not contain the allergens. This enables sharing knowledge on food allergy and help people to aware of their Food Allergen.
                            And have voting system to display the reliability of the content
 
                            
                            2.Profile for User - There are people with different Food Allergens, and they have their own specific Allergies, so we must customize them for the user.
                            The profile will take user details of food allergy. This help user to identify other user through the forum and its easy to make a specific suggestion 
                            according to user Allergy 


                            
                            3.Process Accurate Recipes - In our method, a food context and allergy-free substitute ingredients are extracted from known food recipes, 
                            and they are attached to unknown food recipes based on the similarity between recipes. In order to calculate the recipe similarity,
                            we extract the following recipe metadata from each recipe.

                                A.	Metadata of food ingredient (Ingredient metadata)
                                B.	Metadata of food equipment (Equipment metadata)
                                C.	Metadata of food context (Context metadata)
                                
                            The information of ingredients and equipment is extracted and used as keywords in a recipe.
                            The context metadata is a set of keywords for capturing the food context in the targeted dish, such as the function of an allergic component, the texture,
                            and the color, which are not explicitly defined in the recipe.
                            As a result, in this study, a cooking expert extracts context metadata from a small portion of the recipe.
                            To find allergy-free ingredients for a specific recipe, our method first computes the similarity between the targeted recipe and other recipes stored in a 
                            database using the above recipe metadata.
                            Then, using our method, we extract allergy-free ingredients with scores, which are then associated with recipes in the top rank.
                            Furthermore, each allergy-free ingredient's total score is computed, and the allergy-free ingredients with the highest total scores 
                            are recommended for the targeted food


                            4.	Food Restriction Filter System - The purpose of this component is to avoid the user's food restrictions among the allergy free food recipes.
                            Each recipe is categorized into specific user’s food preferences. The user's preferred food restriction is avoided, and that should be altered with the 
                            user’s permitted ingredient.



<!-------------------------------------------------------------------------------------------------------------------------->
*Member 3: IT19212050 Muhassan Faizal*
**Individual Component** : KEEP TRACKING OF FOOD INTAKE, NUTRIENTS AND CALORIES IN THE FOOD DIARY AND SUGGESTING MEAL PLAN.
**Individual research question** : Providing accurate information is viable in developing this part of app so how and where are we going to find  valid sources to provide such information is a challenge we are facing, however to overcome this particulat problem we ll be using a methodology called webscraping.it allows to collect the neccessary information from a relevant website and accuracy of those datasources of has to be too checked.

Suggesting a Tailored meal plan for every users  depending on their preferences.First and Foremost inorder to suggest a mealplan we need to analyse the users eating pattern.considering the eating pattern we determine what the users nutritional composition is lookalike and based on that we start predictions and suggestions of meal plans relevant to user preferences

**Individual Objectives**- *Member 3: Muhassan Faizal*
Suggesting and Predicting a perfect meal plan for the user as per their eating pattern is by far the main                           goal of the componenet 

**Sub Objectives:** :An App concerned with providing a better user experience to the user.
Track Calories of foods and at the same time make the user able to view their nutritional information too 























