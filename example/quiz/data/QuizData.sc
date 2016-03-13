/**
 * Some predefined Quiz data.
 */
public class QuizData {

   static object quizList extends ComponentList<Quiz> {
      object artHistoryQuiz extends Quiz {
         name = "Art History";
 
         object artQuestions extends ComponentList<Question> {
            object question1 extends Question {
               question = "Andy Warhol was a leading figure in the visual art movement " + 
	                  "known as: ";
    	       answerChoices =  { "Cubism", "Dadaism", "Art Nouveau", "Pop art" };
	       answerIndex = 3;
	       answerDetail = "Best known as the artist who painted the Campbell's Soup " + 
	                      "Can, Warhol was one of the leaders of pop art in the " + 
			      "United States.";
            }

            object question2 extends Question {
               question = "Which of the following statements about the Leaning Tower of " + 
	                  "Pisa is incorrect?";
	       answerChoices =  { "It is a freestanding bell tower",
				  "It took almost two centuries to complete",
				  "The architect intended for it to lean",
				  "It leans at an angle of about 4 degrees" };
	       answerIndex = 2;
	       answerDetail = "Intended to stand vertically, the tower began leaning " + 
	                      "soon after the onset of construction, due to a poorly " + 
			      "laid foundation set in weak, unstable subsoil.";
            }

            object question3 extends Question {
               question = "What is the name of a painting technique in which small, distinct " + 
	                  "dots of pure color are applied in patterns to form an image?";
               answerChoices =  { "Impressionism", "Pointillism",
				  "Surrealism", "Expressionism" };
	       answerIndex = 1;
	       answerDetail = "Pointillism, branching from Impressionism, was developed " + 
	                      "by George Seurat in 1886.";
            }

            object question4 extends Question {
               question = "What is the most common theme encountered in prehistoric " +	
	                  "cave paintings?";
	       answerChoices =  { "Human gatherings", "Religious rituals",
				  "Natural disasters", "Wild animals" };
	       answerIndex = 3;
	       answerDetail = "Cave paintings often pictured large wild animals, such " + 
	                      "as bison, horses, and deer. Drawings of humans were rare " + 
			      "and were usually schematic.";
            }

            object question5 extends Question {
               question = "How many paintings did Vincent van Gogh sell in his lifetime?";
               answerChoices =  { "One", "Sixteen", "Fifty four", "One hundred" };
	       answerIndex = 0;
	       answerDetail = "Little appreciated during his lifetime, van Gogh died " + 
	                      "largely unknown to the art world at the age of 37.";
            }
         }

         questions = artQuestions;
      }

      object geographyQuiz extends Quiz {
         name = "Geography";
 
         object geographyQuestions extends ComponentList<Question> {
            object question1 extends Question {
               question = "What is the tallest mountain in Western Europe?";
    	       answerChoices =  { "Mont Blanc", "Matterhorn", "Monte Rosa", "Gran Sasso" };
	       answerIndex = 0;
	       answerDetail = "Part of the Alps mountain range, Mont Blanc " + 
	                      "rises 4,810 meters above sea level.";
            }

            object question2 extends Question {
               question = "Which of these Asian countries is most populous?";
               answerChoices =  { "North Korea", "Thailand",  "Philippines", "Vietnam" };
	       answerIndex = 2;
	       answerDetail = "With an estimated population of about 92 million, " + 
	                      "the Philippines is the world's 12th most populous " + 
			      "country.";
            }

            object question3 extends Question {
               question = "Portuguese is the official language in all of the " + 
	                  "following countries, except: ";
               answerChoices =  { "Portugal", "Andorra", "Brazil", "Angola" };
	       answerIndex = 1;
	       answerDetail = "Portuguese is also one of the official languages " +
	                      "of Macau (with Chinese) and East Timor (with Titum).";
            }

            object question4 extends Question {
               question = "What is the currency of Estonia?";
	       answerChoices =  { "Kroon", "Litas", "Kuna", "Euro" };
	       answerIndex = 0;
	       answerDetail = "The word kroon, meaning \"crown,\" is related to that " + 
	                      "of other Nordic currencies, such as the Swedish krona " + 
			      "and Norwegian krone.";
            }

            object question5 extends Question {
               question = "What was the location of the most powerful earthquake " + 
	                  "ever recorded?";
	       answerChoices =  { "Anchorage, Alaska", "Lisbon, Portugal", 
				  "Mexico City, Mexico", "Valdivia, Chile" };
	       answerIndex = 3;
	       answerDetail = "The 1960 Valdivia earthquake, also known as the Great " + 
	                      "Chilean Earthquake, had a Richter magnitude of 9.5.";
            }
         }

         questions = geographyQuestions;
      }

      object historyQuiz extends Quiz {
         name = "History";
 
         object historyQuestions extends ComponentList<Question> {
            object question1 extends Question {
               question = "The term \"Dark Ages\" refers to a period in Western European " +
	                  "history marked by: ";
    	       answerChoices =  { "A series of deadly plagues", "A prolonged solar eclipse", 
				  "Cultural and economic decline", "The rise of the Crusades" };
	       answerIndex = 2;
	       answerDetail = "The Dark Ages is the perceived period of cultural, intellectual, " + 
	                      "and economic darkness following the decline of the Roman Empire.";
            }

            object question2 extends Question {
               question = "The start of World War II is generally held to be September 1, " + 
	                  "1939. What event occurred on that day to trigger the war?";
               answerChoices =  { "Assassination of Archduke of Austria", 
				  "German invasion of Poland",  
				  "Japanese attack on Pearl Harbor",
				  "German annexation of Austria" };
	       answerIndex = 1;
	       answerDetail = "Britain and France declared war on Germany two days after " + 
	                      "the German invasion of Poland.";
            }

            object question3 extends Question {
               question = "Which of the following Founding Fathers did not go on to become " +
	                  "President of the United States?";
               answerChoices =  { "George Washington", "Thomas Jefferson", 
				  "Benjamin Franklin", "James Madison" };
	       answerIndex = 2;
	       answerDetail = "One of the most influential Founding Fathers, Benjamin " + 
	                      "Franklin is sometimes referred to as \"the only President " + 
			      "of the United States who was never President of the United States.\"";
            }

            object question4 extends Question {
               question = "Of the following Napoleonic battles, which one was a defeat " + 
	                  "for Napoleonic France?";
	       answerChoices =  { "Battle of Borodino", "Battle of the Pyramids",
				  "Battle of Marengo", "Battle of Trafalgar" };
	       answerIndex = 3;
	       answerDetail = "The Battle of Trafalgar was the most decisive British naval " + 
	                      "victory of the Napoleonic Wars. Led by Admiral Lord Nelson, " +
			      "the British fleet took 22 of the French ships and lost none.";
            }

            object question5 extends Question {
               question = "The largest contiguous empire in history was:";
	       answerChoices =  { "The Mongol Empire", "The Holy Roman Empire", 
				  "The Ming Empire, China", "The British Empire" };
	       answerIndex = 0;
	       answerDetail = "Founded by Genghis Khan in the 13th century, the Mongol Empire " + 
	                      "spanned Eastern Europe and most of Asia. At its greatest " + 
			      "extent, it covered 22% of the Earth's total land area.";
            }
         }

         questions = historyQuestions;
      }

      object mathQuiz1 extends Quiz {
         name = "Math (Easy)";
 
         object mathQuestions extends ComponentList<Question> {
            object question1 extends Question {
               question = "What is 1 + 10 + 1 + 1,000 + 10 + 1 + 10 + 1,000 + 10?";
    	       answerChoices =  { "2,033", "2,403", "243", "2,043" };
	       answerIndex = 3;
	       answerDetail = "There are 2 thousands, 0 hundreds, 4 tens, and 3 ones " + 
	                      "being added together, which makes 2,043.";
            }

            object question2 extends Question {
               question = "How many nickels are in $2.50?";
	       answerChoices =  { "25", "50", "250", "45" };
	       answerIndex = 1;
	       answerDetail = "Twenty nickels make $1 and ten nickels make 50 cents. " + 
	                      "So to make $2.50, we need 20 + 20 + 10 = 50 nickels " + 
	   	   	      "in all.";
            }

            object question3 extends Question {
               question = "Tracy has a job where she works four hours a day, four " + 
	               	  "days a week. How many hours would she work in two weeks?";
               answerChoices =  { "32", "40", "16", "24" };
	       answerIndex = 0;
	       answerDetail = "Tracy would work 4 x 4 = 16 hours in one week, and " + 
	                      "16 x 2 = 32 hours in two weeks.";
            }

            object question4 extends Question {
               question = "What number could go in the blank below to make this " + 
	                  "equation correct?\n" + 
	   	          "                         20 - 6 = __ x 2";
	       answerChoices =  { "16", "14", "7", "8" };
	       answerIndex = 2;
	       answerDetail = "20 - 6 = 7 x 2, since both sides of that equation " + 
	                      "are equal to 14.";
            }

            object question5 extends Question {
               question = "Which of the following is the best estimate of how much " + 
	                  "milk is in a small milk carton that a student might get " + 
	   	          "with lunch at school?";
	       answerChoices =  { "1 liter", "1 gram", "1 pint", "1 gallon" };
	       answerIndex = 2;
	       answerDetail = "A typical milk carton that goes with a school lunch holds " + 
	                      "about one pint of milk.";
            }
         }

         questions = mathQuestions;
      }

      object mathQuiz2 extends Quiz {
         name = "Math (Harder)";
 
         object mathQuestions extends ComponentList<Question> {
            object question1 extends Question {
               question = "If 2 chickens can lay 8 eggs in 3 days, how many eggs can " + 
	                  "5 chickens lay in 6 days?";
    	       answerChoices =  { "18", "24", "32", "40" };
	       answerIndex = 3;
	       answerDetail = "Each of the two chickens will lay 4 eggs in 3 days. " +
	                      "In 6 days, each chicken will lay 8 eggs. Therefore, " + 
			      "five chickens will lay 8 x 5 = 40 eggs in 6 days.";
            }

            object question2 extends Question {
               question = "In a camel herd with 80 legs, half the camels have one " + 
	                  "hump, and half have two. How many humps are there " + 
			  "in the herd?";
               answerChoices =  { "30", "60", "80", "120" };
	       answerIndex = 0;
	       answerDetail = "There are 20 camels in the herd. Ten of them have one " +
	                      "hump, and the other ten have two humps, for a total of " + 
			      "10 + 20 = 30 humps.";
            }

            object question3 extends Question {
               question = "A five-legged alien is pulling socks one at a time from his " + 
	                  "sock drawer. If he only owns green and orange socks, how many " + 
			  "socks must he pull out to be sure to find 5 socks of the same color?";
               answerChoices =  { "5", "9", "10", "25" };
	       answerIndex = 1;
	       answerDetail = "In the worst case, the alien will first pull out 4 green " + 
	                      "socks and 4 orange socks. Once he pulls out the ninth sock, " + 
			      "he is guaranteed to have 5 of the same color.";
            }

            object question4 extends Question {
               question = "One hat and two shirts cost $21. Two hats and one shirt " + 
	                  "cost $18. How much money is needed to buy one hat and " + 
			  "one shirt?";
	       answerChoices =  { "$9", "$11", "$13", "$15" };
	       answerIndex = 2;
	       answerDetail = "Three hats and three shirts cost $21 + $18 = $39. " + 
	                      "One hat and one shirt cost a third of that amount, " + 
			      "or $13.";
            }

            object question5 extends Question {
               question = "When the hour hand of a circular clock goes around once, " + 
	                  "the minute hand goes around:";
	       answerChoices =  { "12 times", "24 times", "60 times", "720 times" };
	       answerIndex = 0;
	       answerDetail = "It takes 12 hours for the hour hand to go around one " + 
	                      "time. The minute hand goes around once for every hour, " + 
			      "so 12 times altogether.";
            }
         }

         questions = mathQuestions;
      }

      object scienceQuiz extends Quiz {
         name = "Science";

         object scienceQuestions extends ComponentList<Question> {
            object question1 extends Question {
               question = "The galaxy we live in is called the Milky Way. It is shaped " + 
   	    	          "approximately like:";
	       answerChoices =  { "A round ball", "A doughnut", 
				  "A pretzel", "A flat spiral" };
	       answerIndex = 3; 
	       answerDetail = "The Milky Way has four spiral arms radiating out from a " +
	       		      "central cluster of stars (nucleus). Our solar system is " +
			      "located on one of the spiral arms, quite far from the " + 
			      "center.";
            }			   

            object question2 extends Question {
               question = "Unlike most other fish, sharks have no:";
   	       answerChoices =  { "Bones", "Teeth", "Gills", "Liver" };
	       answerIndex = 0;
	       answerDetail = "A shark's skeleton is made of cartilage, a material " +
	                      "somewhat softer and more flexible than bone.";
            }

            object question3 extends Question {
               question = "Kinetic energy is:";
	       answerChoices =  { "Energy possessed by living organisms",
				  "Only important at subatomic distances",
				  "Energy of movement",
				  "A rare form of energy occuring in deep space"};
   	       answerIndex = 2;
	       answerDetail = "Anything that moves has kinetic energy. In a collision " +
	       		      "between objects, kinetic energy is transferred from one " + 
			      "object to the other.";
            }

            object question4 extends Question {
               question = "It is now believed that dinosaurs became extinct because of:";
	       answerChoices =  { "Viral diseases", "Hunting by early humans", 
				  "A worldwide period of climatic cooling", 
				  "A meteorite impact" };
   	       answerIndex = 3;
	       answerDetail = "A large meteorite is thought to have collided with the " + 
	                      "earth at the end of the Cretaceous period, some 65 " +
	   		      "million years ago. It probably struck near Mexico's " + 
			      "Yucatan peninsula. The extinctions were caused by " +
			      "climate changes resulting from the collision.";
            }	 

            object question5 extends Question {
               question = "The platypus and the echidna are the only mammals that:";
	       answerChoices =  { "Lay eggs", "Have green blood", 
				  "Live in Antarctica", "Eat eucalyptus leaves" };
   	       answerIndex = 0;
	       answerDetail = "These animals, called monotremes, are true mammals, " + 
	                      "but have some reptile-like features.";
            }
         }

         questions = scienceQuestions;
      }
   }

   static Map<String,Quiz> quizData = createQuizData();

   static Map<String,Quiz> createQuizData() {
      Map<String,Quiz> quizData = new HashMap<String,Quiz>();
      for (Quiz quiz:quizList)
         quizData.put(quiz.name, quiz);
      return quizData;
   }

   /**
    * Returns the names of all the quizes defined in this class.
    */
   public static List<String> getAllQuizNames() {
      List<String> quizNames = new ArrayList<String>();
      for (Quiz quiz:quizList)
         quizNames.add(quiz.name);
      return quizNames;
   }

   /**
    * Looks up a Quiz object by name.
    */
   public static Quiz getQuizByName(String quizName) {
      return quizData.get(quizName);
   }
}

