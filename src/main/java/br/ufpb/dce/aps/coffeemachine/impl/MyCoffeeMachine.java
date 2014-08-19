package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import net.compor.frameworks.jcf.api.ComporFacade;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine extends ComporFacade implements CoffeeMachine {

	private ComponentsFactory factory;
	private ArrayList<Coin> moedas;
	private int[] vetorTroco = new int[6];
	private int cedulas = 0;
	private int centavos = 0;
	boolean condicao = true;
	private final int CAFE = 35;
	


	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info("Insert coins and select a drink!");
		this.moedas = new ArrayList<Coin>();
		this.addComponents();
	}

	@Override
	protected void addComponents() {

		this.add(new CoffeeBlack(this.factory));
		this.add(new CoffeeBlackSugar(this.factory));
		this.add(new CoffeeWhite(this.factory));
		this.add(new CoffeeWhiteSugar(this.factory));
	}

	public void insertCoin(Coin coin) {
		if (coin == null) {
			throw new CoffeeMachineException("Error: Sem Moedas");
		}
		this.moedas.add(coin);
		this.cedulas += coin.getValue() / 100;
		this.centavos += coin.getValue() % 100;
		this.factory.getDisplay().info(
				"Total: US$ " + this.cedulas + "." + this.centavos);
	}

	public void cancel() {
		if (this.cedulas == 0 && this.centavos == 0) {
			throw new CoffeeMachineException("Não houve moeda inserida");
		}
		this.factory.getDisplay().warn(Messages.CANCEL);
		this.devolveMoedas();
	}

	private void limpaMoedas() {
		this.moedas.clear();
	}

	private void devolveMoedas() {
		for (Coin rev : Coin.reverse()) {
			for (Coin aux : this.moedas) {
				if (aux == rev) {
					this.factory.getCashBox().release(aux);
				}
			}
		}
		this.limpaMoedas();
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}


	
	private int[] planejarTroco(int troco) throws CoffeeMachineException {
		int i = 0;
		for (Coin rev : Coin.reverse()) {
			if (rev.getValue() <= troco && factory.getCashBox().count(rev) > 0) {
				while (rev.getValue() <= troco) {
					troco -= rev.getValue();
					this.vetorTroco[i]++;
				}
			}
			i++;
		}
		if (troco != 0) {
			throw new CoffeeMachineException("");
		}
		return this.vetorTroco;
	}
	private void releaseCoins(int[] quantCoin) {
		for (int i = 0; i < quantCoin.length; i++) {
			int count = quantCoin[i];
			Coin coin = Coin.reverse()[i];

			for (int j = 1; j <= count; j++) {
				this.factory.getCashBox().release(coin);
			}
		}
	}
	private int calcularTroco() {
		int count = 0;
		for (Coin rev : Coin.reverse()) {
			for (Coin aux : this.moedas) {
				if (aux == rev) {
					count += aux.getValue();
				}
			}
		}
		return count - this.CAFE;
	}

	public void select(Drink drink) {

		if (calcularTroco() < 0) {
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.devolveMoedas();
			return;
		}
		condicao = (Boolean) requestService("verifyDrinkType", drink);
		if (!condicao) {
			devolveMoedas();
			return;
		}

		try {
			this.vetorTroco = planejarTroco(calcularTroco());
		} catch (Exception e) {
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			this.devolveMoedas();
			return;
		}
		this.factory.getDisplay().info(Messages.MIXING);
		requestService("selectDrinkType", drink);
		requestService("releaseDrink");
		releaseCoins(vetorTroco);

		this.limpaMoedas();
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}
}