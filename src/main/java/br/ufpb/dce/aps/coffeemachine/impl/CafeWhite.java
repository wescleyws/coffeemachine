package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Display;
import br.ufpb.dce.aps.coffeemachine.DrinkDispenser;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class CafeWhite extends Cafe{
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
		
					
		fac.getCupDispenser().contains(1);
		fac.getWaterDispenser().contains(1);
		fac.getCoffeePowderDispenser().contains(1);
		fac.getCreamerDispenser().contains(1.2);
		
		if (!meucafe.planCoins(meucafe.calculaTroco())){
			fac.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			meucafe.removerCoin(fac);
			fac.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		
		fac.getDisplay().info(Messages.MIXING);
		fac.getCoffeePowderDispenser().release(1.9);
		fac.getWaterDispenser().release(1.10);
		fac.getCreamerDispenser().release(1.8);

		fac.getDisplay().info(Messages.RELEASING);
		fac.getCupDispenser().release(1);
		fac.getDrinkDispenser().release(0.9);
		fac.getDisplay().info(Messages.TAKE_DRINK);

		fac.getDisplay().info(Messages.INSERT_COINS);


	}

}
