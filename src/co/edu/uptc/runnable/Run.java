package co.edu.uptc.runnable;

import co.edu.uptc.presenter.Presenter;

import java.io.IOException;
import java.util.function.Consumer;

public class Run {

	public static void main(String[] args) throws IOException {
		Presenter presenter = new Presenter();
		presenter.start();
	}

}
