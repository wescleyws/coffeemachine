package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine{
	private List<Coin> coins;
	private int totalcentavos = 0;
	private ComponentsFactory fac;
	private CashBox box; 
	
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
	
		fac.getDisplay().warn(Messages.CANCEL_MESSAGE);
		removerCoin(fac);
		fac.getDisplay().info(Messages.INSERT_COINS_MESSAGE);			

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
	
}				
		


