package info.openrocket.swing.gui.widgets;

import info.openrocket.core.l10n.Translator;
import info.openrocket.core.simulation.FlightDataType;
import info.openrocket.core.startup.Application;
import info.openrocket.core.unit.Unit;
import info.openrocket.core.unit.UnitGroup;
import info.openrocket.core.util.Group;
import info.openrocket.core.util.Groupable;
import info.openrocket.core.util.UnitValue;
import info.openrocket.swing.gui.components.UnitSelector;
import info.openrocket.swing.gui.plot.Util;
import info.openrocket.swing.gui.util.Icons;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlotTypeSelector<G extends Group, T extends Groupable<G> & UnitValue> extends JPanel {
	private static final Translator trans = Application.getTranslator();
	private static final long serialVersionUID = 9056324972817542570L;

	private final String[] POSITIONS = {Util.PlotAxisSelection.AUTO.getName(),
			Util.PlotAxisSelection.LEFT.getName(), Util.PlotAxisSelection.RIGHT.getName()};

	private final int index;
	private final GroupableAndSearchableComboBox<G, T> typeSelector;
	private final UnitSelector unitSelector;
	private final JComboBox<String> axisSelector;
	private final JButton removeButton;

	public PlotTypeSelector(int plotIndex, T type, Unit unit, int position, List<T> availableTypes) {
		super(new MigLayout("ins 0"));

		this.index = plotIndex;

		typeSelector = new GroupableAndSearchableComboBox<>(availableTypes, trans.get("FlightDataComboBox.placeholder"));
		typeSelector.setSelectedItem(type);
		this.add(typeSelector, "gapright para");

		this.add(new JLabel("Unit:"));
		unitSelector = new UnitSelector(type.getUnitGroup());
		if (unit != null) {
			unitSelector.setSelectedUnit(unit);
		}
		this.add(unitSelector, "width 40lp, gapright para");

		this.add(new JLabel("Axis:"));
		axisSelector = new JComboBox<>(POSITIONS);
		axisSelector.setSelectedIndex(position + 1);
		this.add(axisSelector);

		removeButton = new JButton(Icons.EDIT_DELETE);
		removeButton.setToolTipText("Remove this plot");
		removeButton.setBorderPainted(false);
		this.add(removeButton, "gapright 0");
	}

	public int getIndex() {
		return index;
	}

	public FlightDataType getSelectedType() {
		return (FlightDataType) typeSelector.getSelectedItem();
	}

	public Unit getSelectedUnit() {
		return unitSelector.getSelectedUnit();
	}

	public int getSelectedAxis() {
		return axisSelector.getSelectedIndex() - 1;
	}

	public void addTypeSelectionListener(ItemListener listener) {
		typeSelector.addItemListener(listener);
	}

	public void addUnitSelectionListener(ItemListener listener) {
		unitSelector.addItemListener(listener);
	}

	public void addAxisSelectionListener(ItemListener listener) {
		axisSelector.addItemListener(listener);
	}

	public void addRemoveButtonListener(ActionListener listener) {
		removeButton.addActionListener(listener);
	}

	public void setUnitGroup(UnitGroup unitGroup) {
		unitSelector.setUnitGroup(unitGroup);
	}
}
