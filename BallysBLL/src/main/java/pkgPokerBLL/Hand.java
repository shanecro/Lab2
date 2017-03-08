package pkgPokerBLL;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;



public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();
	
	public Hand()
	{
		
	}
	
	public void AddCardToHand(Card c)
	{
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}
	
	public HandScore getHandScore()
	{
		return HS;
	}
	
	public Hand EvaluateHand()
	{
		Hand h = Hand.EvaluateHand(this);
		return h;
	}
	
	private static Hand EvaluateHand(Hand h)  {

		Collections.sort(h.getCardsInHand());
		
		//	Another way to sort
		//	Collections.sort(h.getCardsInHand(), Card.CardRank);
		
		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	
	
	
	
	
	
	
	
	
	
	//TODO: Implement This Method
	public static boolean isHandRoyalFlush(Hand h, HandScore hs)
	{
		return false;
	}
	
	//TODO: Implement This Method
	public static boolean isHandStraightFlush(Hand h, HandScore hs)
	{
		return false;
	}	
	//TODO: Implement This Method
	public static boolean isHandFourOfAKind(Hand h, HandScore hs)
	{
		boolean isFourOfKind = false;
		if(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() &&
				h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() &&
						h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank()){
			isFourOfKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			
		}
		else if(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() &&
				h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() &&
				h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()){
			isFourOfKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
		}
		hs.setHandStrength(eHandStrength.FourOfAKind);
		return isFourOfKind;
	}	

	
	//TODO: Implement This Method
	public static boolean isHandFlush(Hand h, HandScore hs)
	{
		hs.setHandStrength(eHandStrength.Flush);
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		return isFlush(h);
		
		
	}
	public static boolean isFlush(Hand h){	
		boolean isFlush = false;
		if(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteSuit() &&
				h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteSuit() &&
						h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteSuit() &&
								h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteSuit() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteSuit()){
			isFlush = true;
		}
			else {
				isFlush = false;
			}
		
			return isFlush;
			
	}		
	
	//TODO: Implement This Method
	public static boolean isAce(ArrayList<Card> c){
		if(c.get(0).geteRank() == eRank.ACE){
			return true;}
		
			else {
				return false;
			}
		}

	public static boolean isStraight(ArrayList<Card> c){
		boolean isStraight = true;
		int i = 0;
		if(Hand.isAce(c) == true && c.get(1).geteRank().getiRankNbr() == 5 || c.get(1).geteRank().getiRankNbr() == 13 ){
			i = 1; }
		else {
			i = 0;}
		
		
	for(; i < 3; i++){
		if(c.get(i).geteRank().getiRankNbr() == c.get(i+1).geteRank().getiRankNbr()+1){
			isStraight = true;
			return isStraight;}
		else {
			isStraight = false;
			return isStraight;} 
		

	 }
	return isStraight;
	}
	
	public static boolean isHandStraight(Hand h, HandScore hs)
	{
		if(isStraight(h.getCardsInHand()) == true);{
			hs.setHandStrength(eHandStrength.Straight);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			return true;}
		
		
	}
	//TODO: Implement This Method
	public static boolean isHandThreeOfAKind(Hand h, HandScore hs)
	{
		boolean isThreeOfKind = false;
	
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank()
                && h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank()){
			isThreeOfKind = true;
		}
		else if(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank()
                && h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank()){
			isThreeOfKind = true;
		}
			else if(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank()
	                && h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()){
				isThreeOfKind = true;
		}
		return isThreeOfKind;
		}		
	

	public static boolean isHandTwoPair(Hand h, HandScore hs){
		boolean isTwoPair = false;
	{
        if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank()
                && h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank()){
            isTwoPair = true;
            hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
            hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
        }


            else if(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank()
                    && h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()){
            	isTwoPair = true;
            	hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
            	hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
            }

           else if(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank()
                   && h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()){
        	   isTwoPair = true;
        	   hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
        	   hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
        	   
            }
           else{
        	   isTwoPair = false;
            }
        hs.setHandStrength(eHandStrength.TwoPair);
        return isTwoPair;}
	}
	

	public static boolean isHandPair(Hand h, HandScore hs)
	{   
		boolean isPair = false;
	

		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())){
			isPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}
		else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())){
			isPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
		}
		else if ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
					.get(eCardNo.FourthCard.getCardNo()).geteRank())){
			isPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			}
		else if ((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())){
			isPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());

		}
		hs.setHandStrength(eHandStrength.Pair);
		return isPair;


	}	

	public static boolean isHandHighCard(Hand h, HandScore hs)
	{
		boolean isHighCard = false ;
		
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())){
			if((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand()
					.get(eCardNo.ThirdCard.getCardNo()).geteRank())){
				if((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())){
					if((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.FifthCard.getCardNo()).geteRank())){
						hs.setHandStrength((eHandStrength.HighCard));
						hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
						isHighCard = true;
						return isHighCard;	
					}
				}
			}
			
					}
		return isHighCard;
		
	}	
	
	public static boolean isAcesAndEights(Hand h, HandScore hs)
	{
		return false;
	}	
	
	
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}
}
