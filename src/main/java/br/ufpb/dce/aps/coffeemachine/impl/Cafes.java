package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import net.compor.frameworks.jcf.api.Component;
import net.compor.frameworks.jcf.api.Service;

public class Cafes extends Component{
	
	public Cafes() {
		super("Cafe");
	}
	
		@Service
	public boolean verifyDrinkType(Drink drink) {
		boolean condicao = false;
		if (drink.equals(Drink.BLACK)) {
			condicao = (Boolean) requestService("verifyBlackDrink");
		}

		if (drink.equals(Drink.WHITE)) {
			condicao = (Boolean) requestService("verifyWhiteDrink");
		}

		if (drink.equals(Drink.WHITE_SUGAR)) {
			condicao = (Boolean) requestService("verifyWhiteSugarDrink");
		}

		if (drink.equals(Drink.BLACK_SUGAR)) {
			condicao = (Boolean) requestService("verifyBlackSugarDrink");
		}
		if(drink.equals(Drink.BOUILLON)){
			condicao = (Boolean) requestService("VerificaDrinkBouillon");
			}
		return condicao;
	}

	@Service
	public void selectDrinkType(Drink drink) {
		
		if (drink.equals(Drink.BLACK)) {
			requestService("releaseBlackDrink");
		}

		if (drink.equals(Drink.BLACK_SUGAR)) {
			requestService("releaseBlackSugarDrink");
		}
		if (drink.equals(Drink.WHITE)) {
			requestService("releaseWhiteDrink");
		}

		if (drink.equals(Drink.WHITE_SUGAR)) {
			requestService("releaseWhiteSugarDrink");
		}
			
		if (drink.equals(Drink.BOUILLON)) {
			requestService("releaseBouillon");
		}	
		
	}
}
	
	


