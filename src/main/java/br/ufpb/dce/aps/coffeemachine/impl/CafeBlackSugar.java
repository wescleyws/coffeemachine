package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CafeBlackSugar extends Cafe{
	
	protected Dispenser cupDispenser;
	protected Dispenser waterDispenser;
	protected Dispenser coffeePowderDispenser;
	protected Display display;
	
	public void instanciarDispenser(){
		
		cupDispenser = fac.getCupDispenser();
		waterDispenser = fac.getWaterDispenser();
		coffeePowderDispenser = fac.getCoffeePowderDispenser();
		display = fac.getDisplay();
			
	}

	public void prepararCafe(MyCoffeeMachine meucafe, ComponentsFactory fac){
		
		if (meucafe.calculaTroco() < 0) {
			fac.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			meucafe.removerCoin(fac);
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
		
		fac.getCoffeePowderDispenser().contains(2.3);

		if (!fac.getSugarDispenser().contains(1.2)) {
			fac.getDisplay().warn(Messages.OUT_OF_SUGAR);
			meucafe.removerCoin(fac);
			fac.getDisplay().info(Messages.INSERT_COINS);

			return;
		}
		
		
		
		fac.getDisplay().info(Messages.MIXING);
		fac.getCoffeePowderDispenser().release(1.2);
		fac.getWaterDispenser().release(2.2);
		fac.getSugarDispenser().release(3.2);

		fac.getDisplay().info(Messages.RELEASING);
		fac.getCupDispenser().release(1);
		fac.getDrinkDispenser().release(2.2);
		fac.getDisplay().info(Messages.TAKE_DRINK);

		fac.getDisplay().info(Messages.INSERT_COINS);

	}

}
