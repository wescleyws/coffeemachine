package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CafeBlack extends Cafe {
	
	protected ComponentsFactory fac;
	protected Dispenser cupDispenser;
	protected Dispenser waterDispenser;
	protected Dispenser coffeePowderDispenser;
	protected DrinkDispenser drinkDispenser;
	protected Display display;
	
	public void instanciarDispenser(){
		
		cupDispenser = fac.getCupDispenser();
		waterDispenser = fac.getWaterDispenser();
		coffeePowderDispenser = fac.getCoffeePowderDispenser();
		drinkDispenser = fac.getDrinkDispenser();
		display = fac.getDisplay();
			
	}

	public void prepararCafe(MyCoffeeMachine meucafe, ComponentsFactory fac){
		
		if (meucafe.calculaTroco() < 0) {
			fac.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			meucafe.removerCoin(fac);
			fac.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		
		if (!fac.getCupDispenser().contains(1)) {
			fac.getDisplay().warn(Messages.OUT_OF_CUP);
			meucafe.removerCoin(fac);
			fac.getDisplay().info(Messages.INSERT_COINS);
			return;
		}

		if (!fac.getWaterDispenser().contains(1.2)) {
			fac.getDisplay().warn(Messages.OUT_OF_WATER);
			meucafe.removerCoin(fac);
			fac.getDisplay().info(Messages.INSERT_COINS);
			return;
		}

		if (!fac.getCoffeePowderDispenser().contains(1.2)) {
			fac.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			meucafe.removerCoin(fac);
			fac.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		
		fac.getDisplay().info(Messages.MIXING);
		fac.getCoffeePowderDispenser().release(1.5);
		fac.getWaterDispenser().release(2.5);

		fac.getDisplay().info(Messages.RELEASING);
		fac.getCupDispenser().release(1);
		fac.getDrinkDispenser().release(anyDouble());
		fac.getDisplay().info(Messages.TAKE_DRINK);
		fac.getDisplay().info(Messages.INSERT_COINS);
	}


}
