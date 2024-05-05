package co.edu.uptc.runnable;

import java.util.function.Consumer;

import co.edu.uptc.presenter.Presenter;

// TODO: Auto-generated Javadoc
/**
 * The Class Run.
 */
public class Run {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Consumer<Presenter> presenterConsumer = presenter -> presenter.loadData();
		presenterConsumer.accept(new Presenter());
	}

}
