package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine{
	private List<Coin> coins;
	private int totalcentavos = 0;
	private ComponentsFactory fac;
	private CashBox box; 
	private Drink drinkSelect;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		fac = factory;
		fac.getDisplay().info("Insert coins and select a drink!");
		box = fac.getCashBox();
		this.coins = new ArrayList<Coin>();
		
	}

	public void insertCoin(Coin coin) {
		
		
		if (coin == null){
			throw new CoffeeMachineException("Moeda não aceita");
		}
		
		this.coins.add(coin);
		
		totalcentavos += coin.getValue();
		fac.getDisplay().info("Total: US$ " + totalcentavos/100 + "." + totalcentavos%100 );
		}
		
	public void cancel() {
		if(this.totalcentavos == 0){
			throw new CoffeeMachineException("Não possui moedas inseridas");
			}
	
		fac.getDisplay().warn(Messages.CANCEL);
		removerCoin(fac);
		fac.getDisplay().info(Messages.INSERT_COINS);


		}
	
	private void removerCoin(ComponentsFactory factory) {
		List<Integer> remover = new ArrayList<Integer>();

		for (Coin coin : Coin.reverse()) {
			for (int i = 0; i < this.coins.size(); i++) {
				if (this.coins.get(i).equals(coin)) {
					fac.getCashBox().release(coins.get(i));
					remover.add(new Integer(i));
				}
			}
		}
		this.coins.removeAll(remover);
	}

	
		public void select(Drink drink) {
			
			
			switch (drink){
			
			case BLACK :
				
				fac.getCupDispenser().contains(1); 
				fac.getWaterDispenser().contains(1.5);
			
				if(!fac.getCoffeePowderDispenser().contains(1.2)){
					fac.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
					removerCoin(fac);
					fac.getDisplay().info(Messages.INSERT_COINS);

						return;	}
			
				
			fac.getDisplay().info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.5);
			fac.getWaterDispenser().release(2.5);

			fac.getDisplay().info(Messages.RELEASING);
			fac.getCupDispenser().release(1);
			fac.getDrinkDispenser().release(anyDouble());
			fac.getDisplay().info(Messages.TAKE_DRINK);
			
			fac.getDisplay().info(Messages.INSERT_COINS);
			
			break;

			case BLACK_SUGAR : 
			
			fac.getCupDispenser().contains(1);
			fac.getWaterDispenser().contains(0.2);
			fac.getCoffeePowderDispenser().contains(2.3);
			fac.getSugarDispenser().contains(1.4);
		
			fac.getDisplay().info(Messages.MIXING);
			fac.getCoffeePowderDispenser().release(1.2);
			fac.getWaterDispenser().release(2.2);
			fac.getSugarDispenser().release(3.2);

			fac.getDisplay().info(Messages.RELEASING);
			fac.getCupDispenser().release(1);
			fac.getDrinkDispenser().release(2.2);
			fac.getDisplay().info(Messages.TAKE_DRINK);
		
			fac.getDisplay().info(Messages.INSERT_COINS);
			
			coins.clear();
		
		}	
	
		
}
	
}
		

				
		


