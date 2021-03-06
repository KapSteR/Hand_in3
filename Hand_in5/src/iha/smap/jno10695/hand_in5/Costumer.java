package iha.smap.jno10695.hand_in5;

public class Costumer {

	// private variables
	int _id;
	String _name;
	String _address;

	// Empty constructor
	public Costumer() {

	}

	// constructor
	public Costumer(int id, String name, String _address) {
		this._id = id;
		this._name = name;
		this._address = _address;
	}

	// constructor
	public Costumer(String name, String _address) {
		this._name = name;
		this._address = _address;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	// getting phone number
	public String getAddress() {
		return this._address;
	}

	// setting phone number
	public void setAddress(String address) {
		this._address = address;
	}
}
