package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;
import net.compor.frameworks.jcf.api.ComporFacade;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine{

	private int totalcentavos = 0;
	private ComponentsFactory fac;
	private CashBox box; 
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		fac = factory;
		fac.getDisplay().info("Insert coins and select a drink!");
		box = fac.getCashBox();
	}

	public void insertCoin(Coin coin) {
		
		if (coin == null){
			throw new CoffeeMachineException("Moeda não aceita");
		}
		
		totalcentavos += coin.getValue();
		fac.getDisplay().info("Total: US$ " + totalcentavos/100 + "." + totalcentavos%100 );
		}

	public void cancel() {
		if(this.totalcentavos == 0){
			throw new CoffeeMachineException("Não possui moedas inseridas");
			}
	
		fac.getDisplay().warn(Messages.CANCEL_MESSAGE);
		box.release(Coin.halfDollar);
		fac.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
	
		}	
}
