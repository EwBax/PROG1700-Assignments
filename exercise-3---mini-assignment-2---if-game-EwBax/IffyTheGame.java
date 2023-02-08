import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class IffyTheGame {
	public static void main(String[] args) {

		Random random = new Random();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Iffy's Adventure. We welcome all adventurers. What is your name? ");
		String name = scanner.nextLine();

		System.out.println("Hello " + name
				+ ", what type of adventurer are you? Enter [1] for fighter, [2] for ranger, or [3] for wizard.");

		int choice;

		try {
			choice = scanner.nextInt(); // Only accepting an int
		} catch (java.util.InputMismatchException e) {
			scanner.nextLine();
			choice = 4;
		}

		// creating player Character object to use later
		Character player = new Character(name, choice);

		if (choice == 1) {
			System.out.println("Ah, so you're a fighter! Take this sword then - it's dangerous to go alone.\n");
		} else if (choice == 2) {
			System.out.println("Of course, you look like the ranger choice! You can have my bow and arrows - here.\n");
		} else if (choice == 3) {
			System.out.println("A wizard eh? Here, take this staff to help you channel spells.\n");
		} else {
			System.out.println(
					"Oh you think yourself a clever one eh? Must be a rogue - I'm sure you've got your own weapons then.\n");
		}

		System.out.println("Here's some info about you:\n" + player.toString() + "\n");

		System.out.println("Alright " + player.name
				+ ", there are three mystery doors for you to choose from. Please select a path by entering [1], [2], or [3].");

		// reusing variable from earlier since it isnt needed anymore
		choice = 0;
		// boolean to tell if it is the first time through a loop
		boolean firstTime = true;

		// choosing their path
		while (choice != 1 && choice != 2 && choice != 3) {
			try {
				if (firstTime == false) {
					System.out.println(
							"It was kind of funny the first time but now you're just being annoying. Please enter [1], [2], or [3].");
				}
				choice = scanner.nextInt();
				firstTime = false;
			} catch (java.util.InputMismatchException e) {
				scanner.nextLine();
				firstTime = false;
				System.out.println(
						"It was kind of funny the first time but now you're just being annoying. Please enter [1], [2], or [3].");
			}
		}

		// pirate attack path
		if (choice == 1) {
			System.out.println("You enter the first mystery door, and are greeted by the smell of the ocean." +
					"You find yourself on the deck of a merchant galleon amidst a battle, and the door you came through is nowhere to be seen. "
					+
					"It appears the ship you're on is being attacked by pirates... or are you one of them?");

			// Merchant Or Pirate
			choice = 0;
			while (choice != 1 && choice != 2) {
				try {
					System.out.println("Enter [1] for merchant, [2] for pirates:");
					choice = scanner.nextInt();
				} catch (java.util.InputMismatchException e) {
					scanner.nextLine();
				}
			}

			// Merchant path
			if (choice == 1) {
				System.out.println(
						"You inspect your clothing and realize you are one of the merchants. One of your crewmates shouts for your attention as they make their way towards you - "
								+
								"the captain of your vessel hanging onto their shoulder. They usher you into the captain's quarters, sit the captain on a table, and shut the door behind you.\n");
				System.out.println("'The captain is injured! You stay and protect him, I'll go help outside!");
				System.out.println("They open the door and let out a battle cry as they disappear into the chaos. " +
						"Moments later the shadow of a figure appears in the doorway, the scimitar in their hand reflecting the sunlight - judging by the hat, this is the captain of the pirate crew.");
				System.out.println(
						"'Arrrr, I've got nothing against ye' or yer crew matey, it be yer captain and yer booty that I be here for.'");
				System.out.println(
						"'If ye' step aside and drop yer weapon, I'll give ye a chance to join my crew. But get between me and my prize and I'll cut ye' down! What'll it be!?");

				choice = 0;
				while (choice != 1 && choice != 2) {
					try {
						System.out.println("Enter [1] to step aside or [2] to stand and fight:");
						choice = scanner.nextInt();
					} catch (java.util.InputMismatchException e) {
						scanner.nextLine();
						System.out.println("Enter [1] to step aside or [2] to stand and fight:");
					}
				}

				if (choice == 1) {
					// stand aside
					System.out.println(
							"You step aside and drop your weapon. As the pirate captain begins to move forward, suddenly your captain stirs. Leaping up from the table - his wounds magically healed - "
									+
									"he pulls out his sword and yells at you.");
					System.out.println(
							"'You traitor! You would abandon your crew so easily, after all we've been through? Lucky for me I had this healing potion stashed away.'");
					System.out.println(
							"He tosses the empty potion bottle in his hand, the glass shattering on the wooden floor of his cabin.\n "
									+
									"'You'll pay for this!'");
					System.out.println(
							"Without a second of hesitation your - now former - captain lunges at you with his sword.");

					Boss captain = new Boss("captain");
					scanner.nextLine();
					while (captain.hitPoints > 0 && player.hitPoints > 0) {
						System.out.println("Press [ENTER] to attack:");
						scanner.nextLine();
						captain.getAttacked(player.attack(captain.creatureType));
						if (captain.hitPoints > 0) {
							player.getAttacked(captain.attack(), captain.creatureType);
							player.health();
						}
					}

					if (captain.hitPoints < 1) {
						// if you win
						System.out.println(
								"And with that final blow your former captain collapses to the ground - dead. The pirate captain laughs and looks at you, "
										+
										"'Welcome to the crew!'");
						System.out.println(
								"Without their captain to lead them the merchant crew is quickly defeated by the pirates. You are given a share of the booty, and welcomed into your new crew.");
						System.out.println(
								"Suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					} else {
						// if you lose
						System.out.println(
								"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
						System.out.println(
								"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					}

				} else {
					// stand ground and fight pirate
					System.out.println(
							"You stand your ground to protect your captain. The pirate captain chuckles, 'alright then!' and lunges at you.");
					Boss pirateCaptain = new Boss("pirate captain");
					scanner.nextLine();
					while (pirateCaptain.hitPoints > 0 && player.hitPoints > 0) {
						System.out.println("Press [ENTER] to attack:");
						scanner.nextLine();
						pirateCaptain.getAttacked(player.attack(pirateCaptain.creatureType));
						if (pirateCaptain.hitPoints > 0) {
							player.getAttacked(pirateCaptain.attack(), pirateCaptain.creatureType);
							player.health();
						}
					}

					if (pirateCaptain.hitPoints < 1) {
						// if you win
						System.out
								.println("And with that final blow the pirate captain collapses to the ground - dead.");
						System.out.println(
								"You hear another pirate shout, 'The captain is dead! Retreat!' and the pirates begin to flee back to their vessel.");
						System.out.println(
								"Suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					} else {
						// if you lose
						System.out.println(
								"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
						System.out.println(
								"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					}
				}
			} else {
				// you are a pirate
				System.out.println(
						"You look down at your clothing and realize you are one of the pirates. One of your crew mates shouts at you from across the deck:");
				System.out.println("'The treasure is down below deck, come help me!'");
				System.out.println(
						"You follow them below deck and find a large treasure chest, guarded by two of the merchant sailors.\n");
				System.out.println("'We'll each take one of them, and the treasure will be ours!'");
				System.out.println("As if on queue, the merchant sailors each move to attack you and your partner.");

				Boss sailor = new Boss("sailor");
				scanner.nextLine();
				while (sailor.hitPoints > 0 && player.hitPoints > 0) {
					System.out.println("Press [ENTER] to attack:");
					scanner.nextLine();
					sailor.getAttacked(player.attack(sailor.creatureType));
					if (sailor.hitPoints > 0) {
						player.getAttacked(sailor.attack(), sailor.creatureType);
						player.health();
					}
				}

				if (sailor.hitPoints < 1) {
					// if you win
					System.out.println(
							"And with that final blow the sailor collapses to the ground - dead. Your pirate friend is victorious as well. The two of you grab the treasure chest and carry it upstairs.");
					System.out.println(
							"When you reach the deck it appears the battle is over, and the pirates are victorious. The pirate captain comes to inspect the treasure, and gives you a reward for locating it.");
					System.out.println(
							"Suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				} else {
					// if you lose
					System.out.println(
							"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
					System.out.println(
							"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				}
			}
		} else if (choice == 2) {
			// arena path
			System.out.println(
					"You enter mystery door number two, and are greeted by the sound of a roaring crowd. Looking around, you appear to be in a Roman style collisseum - the stands full of people. "
							+
							"Looking down at yourself you find you are dressed like a gladiator. Suddenly, the voice of a man standing next to you booms and the crows hushes.");
			System.out.println(
					"'Welcome one and all, to the great arena! For your enjoyment today we have one of our greatest warriors - "
							+ player.name + " - here today. They have chosen to face off against...'");
			System.out.println(
					"They trail off and lean close to you, then quietly they ask 'what did you choose to battle again? I've forgotten.'");

			choice = 0;
			while (choice != 1 && choice != 2 && choice != 3) {
				try {
					System.out
							.println("Enter [1] to fight the lion, [2] to fight the tiger, or [3] to fight the bear:");
					choice = scanner.nextInt();
				} catch (java.util.InputMismatchException e) {
					scanner.nextLine();
				}
			}

			if (choice == 1) {
				// lion
				System.out.println("'Oh yes, the lion. Ahem.. The great " + player.name
						+ " has chosen to face off against the fearsome LION!'");
				System.out.println(
						"The crowd roars with excitement as the accouncer walks off. A few moments after they exit, the large portcullis on the far side of the arena opens and a large, hungry looking lion walks out. "
								+
								"The lion looks around for a second before its gaze falls on you. It lets out a fearsome roar and charges!");

				Boss lion = new Boss("lion");
				scanner.nextLine();
				while (lion.hitPoints > 0 && player.hitPoints > 0) {
					System.out.println("Press [ENTER] to attack:");
					scanner.nextLine();
					lion.getAttacked(player.attack(lion.creatureType));
					if (lion.hitPoints > 0) {
						player.getAttacked(lion.attack(), lion.creatureType);
						player.health();
					}
				}

				if (lion.hitPoints < 1) {
					// if you win
					System.out.println(
							"And with that final blow the lion collapses to the ground - dead. The crowd goes crazy! You hear them chanting your name, "
									+ player.name.toUpperCase() + "! " + player.name.toUpperCase() + "! "
									+ player.name.toUpperCase() + "!");
					System.out.println(
							"You exit the arena, and suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				} else {
					// if you lose
					System.out.println(
							"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
					System.out.println(
							"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				}
			} else if (choice == 2) {
				// tiger
				System.out.println("'Oh yes, the tiger. Ahem.. The great " + player.name
						+ " has chosen to face off against the fearsome TIGER!'");
				System.out.println(
						"The crowd roars with excitement as the accouncer walks off. A few moments after they exit, the large portcullis on the far side of the arena opens and a large, hungry looking tiger walks out. "
								+
								"The tiger looks around for a second before its gaze falls on you. It lets out a fearsome roar and charges!");

				Boss tiger = new Boss("tiger");
				scanner.nextLine();
				while (tiger.hitPoints > 0 && player.hitPoints > 0) {
					System.out.println("Press [ENTER] to attack:");
					scanner.nextLine();
					tiger.getAttacked(player.attack(tiger.creatureType));
					if (tiger.hitPoints > 0) {
						player.getAttacked(tiger.attack(), tiger.creatureType);
						player.health();
					}
				}

				if (tiger.hitPoints < 1) {
					// if you win
					System.out.println(
							"And with that final blow the tiger collapses to the ground - dead. The crowd goes crazy! You hear them chanting your name, "
									+ player.name.toUpperCase() + "! " + player.name.toUpperCase() + "! "
									+ player.name.toUpperCase() + "!");
					System.out.println(
							"You exit the arena, and suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				} else {
					// if you lose
					System.out.println(
							"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
					System.out.println(
							"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				}
			} else {
				// bear
				System.out.println("'Oh yes, the bear. Ahem.. The great " + player.name
						+ " has chosen to face off against the fearsome BEAR!'");
				System.out.println(
						"The crowd roars with excitement as the accouncer walks off. A few moments after they exit, the large portcullis on the far side of the arena opens and a large, hungry looking bear walks out. "
								+
								"The bear looks around for a second before its gaze falls on you. It lets out a fearsome roar and charges!");

				Boss bear = new Boss("bear");
				scanner.nextLine();
				while (bear.hitPoints > 0 && player.hitPoints > 0) {
					System.out.println("Press [ENTER] to attack:");
					scanner.nextLine();
					bear.getAttacked(player.attack(bear.creatureType));
					if (bear.hitPoints > 0) {
						player.getAttacked(bear.attack(), bear.creatureType);
						player.health();
					}
				}

				if (bear.hitPoints < 1) {
					// if you win
					System.out.println(
							"And with that final blow the bear collapses to the ground - dead. The crowd goes crazy! You hear them chanting your name, "
									+ player.name.toUpperCase() + "! " + player.name.toUpperCase() + "! "
									+ player.name.toUpperCase() + "!");
					System.out.println(
							"You exit the arena, and suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				} else {
					// if you lose
					System.out.println(
							"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
					System.out.println(
							"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				}
			}
		} else {
			System.out.println(
					"You enter the third mystery door and find yourself in front of a small log cabin in a forest. A few seconds later, the front door to the cabin opens and a mysterious hooded figure appears.");
			System.out.println(
					"The figure speaks, and their voice sounds frail and weak - 'Ahh another adventurer seeking to claim my riches eh? Very well, first you must pass my trial!'");
			System.out.println(
					"'Will it be trial by combat, or will you attempt to use your intelligence to answer my riddle?'");

			choice = 0;
			while (choice != 1 && choice != 2) {
				try {
					System.out.println("Enter [1] for trial by combat, or [2] to answer the riddle");
					choice = scanner.nextInt();
				} catch (java.util.InputMismatchException e) {
					scanner.nextLine();
				}
			}

			if (choice == 1) {
				System.out.println(
						"'Ahh trial by combat eh? I must warn you, the creatures you may face are like nothing you have ever seen before. None have ever survived. You still wish to proceed? Very well.'");
				System.out.println(
						"You will choose which creature to face, but it will be a blind choice. Will you face creature 1, or 2?");

				choice = 0;
				while (choice != 1 && choice != 2) {
					try {
						System.out.println("Enter [1] or [2]:");
						choice = scanner.nextInt();
					} catch (java.util.InputMismatchException e) {
						scanner.nextLine();
					}
				}

				if (choice == 1) {
					System.out.println(
							"You choose creature 1. Almost immediately after you make your choice, the hooded figure begins to channel a spell, and some sort of portal appears in front of you.");
					System.out.println(
							"Moments later, a spider the size of a car appears. You barely have time to dodge as it launches itself into an attack on you.");

					Boss spider = new Boss("spider");
					scanner.nextLine();
					while (spider.hitPoints > 0 && player.hitPoints > 0) {
						System.out.println("Press [ENTER] to attack:");
						scanner.nextLine();
						spider.getAttacked(player.attack(spider.creatureType));
						if (spider.hitPoints > 0) {
							player.getAttacked(spider.attack(), spider.creatureType);
							player.health();
						}
					}
					if (spider.hitPoints < 1) {
						// if you win
						System.out.println(
								"And with that final blow the spider collapses to the ground - dead. You look up to where the hooded figure was, but they are nowhere to be seen. The door to the cabin remains open.");
						System.out.println(
								"You enter the cabin and find riches beyond your wildest dreams. You stuff your pockets with as much treasure as you can hold, and exit the cabin.");
						System.out.println(
								"Suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					} else {
						// if you lose
						System.out.println(
								"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
						System.out.println(
								"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					}
				} else {
					System.out.println(
							"You choose creature 2. Almost immediately after you make your choice, the hooded figure begins to channel a spell, and some sort of portal appears in front of you.");
					System.out.println(
							"Moments later, a grotesque creature emerges from the portal. It looks like a lion, but there is also the head of a goat protruding from its back and its tail is a snake. "
									+
									"You barely have time to dodge as it launches itself into an attack on you.");

					Boss chimera = new Boss("chimera");
					scanner.nextLine();
					while (chimera.hitPoints > 0 && player.hitPoints > 0) {
						System.out.println("Press [ENTER] to attack:");
						scanner.nextLine();
						chimera.getAttacked(player.attack(chimera.creatureType));
						if (chimera.hitPoints > 0) {
							player.getAttacked(chimera.attack(), chimera.creatureType);
							player.health();
						}
					}
					if (chimera.hitPoints < 1) {
						// if you win
						System.out.println(
								"And with that final blow the chimera collapses to the ground - dead. You look up to where the hooded figure was, but they are nowhere to be seen. The door to the cabin remains open.");
						System.out.println(
								"You enter the cabin and find riches beyond your wildest dreams. You stuff your pockets with as much treasure as you can hold, and exit the cabin.");
						System.out.println(
								"Suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					} else {
						// if you lose
						System.out.println(
								"And with that final blow you collapse to the ground - dead. Your vision fades to black, then slowly returns");
						System.out.println(
								"You are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
					}
				}
			} else {
				System.out.println("'So, you thought the riddle would be easier eh? We shall see.'");
				System.out.println(
						"'Here is your riddle adventurer: what is the airspeed velocity of an unladen Swallow?");
				choice = 0;
				while (choice != 1 && choice != 2) {
					try {
						System.out.println(
								"Enter [1] to reply '11 meters per second', or [2] to reply 'African or European?'");
						choice = scanner.nextInt();
					} catch (java.util.InputMismatchException e) {
						scanner.nextLine();
					}
				}
				if (choice == 1) {
					System.out.println(
							"'WRONG!' The figure shouts, then with a flick of their wrist you are launched into the air. As you get higher and higher you begin to feel lightheaded, and eventually black out.");
					System.out.println(
							"You wake up and find yourself back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				} else {
					System.out.println(
							"'What? I don't know!' the figure replies, before a second later they are launched into the air. The door to the cabin is now unguarded.");
					System.out.println(
							"You enter the cabin and find riches beyond your wildest dreams. You stuff your pockets with as much treasure as you can hold, and exit the cabin.");
					System.out.println(
							"Suddenly you are back in the lobby of Iffy's Adventures, facing the exit. A neon sign over the door reads 'Thanks for coming, we hope you had a fun adventure!'");
				}
			}

		}
	}
}

// Player character object
class Character {
	public String name;
	public String type;
	public int hitPoints = 15;

	// creator to set name and type
	public Character(String name, int type) {
		this.name = name;
		if (type == 1) {
			this.type = "fighter";
		} else if (type == 2) {
			this.type = "ranger";
		} else if (type == 3) {
			this.type = "wizard";
		} else {
			this.type = "rogue";
		}
	}

	// attack method for boss battle
	public int attack(String creatureType) {

		Random rand = new Random();
		int damage = rand.nextInt(9) + 1;

		if (this.type.equals("fighter")) { // create boss object with type for 'creature'
			System.out.println("You swing your sword and hit the " + creatureType + " for " + damage + " damage!");
		} else if (this.type.equals("ranger")) {
			System.out.println("You loose an arrow at the " + creatureType + " and hit it for " + damage + " damage!");
		} else if (this.type.equals("wizard")) {
			System.out.println(
					"You send a bolt of fire at the " + creatureType + " and hit it for " + damage + " damage!");
		} else {
			System.out.println("You stab the " + creatureType + " with your hidden dagger for " + damage + " damage!");
		}

		return damage;
	}

	// health method for taking damage and adjusting hit points
	public void getAttacked(int damage, String creatureType) {
		this.hitPoints = this.hitPoints - damage;
		if (this.hitPoints < 0) {
			this.hitPoints = 0;
		}
		System.out.println("The " + creatureType + " hits you for " + damage + " damage!");
	}

	public void health() {
		System.out.println("You have " + this.hitPoints + " hit points remaining!");
	}

	// method for testing
	public String toString() {
		return "Name = " + this.name + "\nType = " + this.type + "\nHit Points = " + this.hitPoints;
	}
}

class Boss {
	public String creatureType;
	public int hitPoints = 10;

	public Boss(String creatureType) {
		this.creatureType = creatureType;
	}

	public int attack() {

		Random rand = new Random();
		int damage = rand.nextInt(9) + 1;

		return damage;
	}

	public void getAttacked(int damage) {
		this.hitPoints = this.hitPoints - damage;
	}
}
