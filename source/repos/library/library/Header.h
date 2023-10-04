#pragma once
#include <string>
#include <vector>

class Animal {
private:
	std::string name;
	std::string weight;
	std::string kind;
	std::string sex;

public:
	Animal() = default;
	Animal(std::string setName, std::string setKind, std::string setSex, std::string setWeight) {	//конструктор со всеми аргументами
		name = setName;
		weight = setWeight;
		kind = setKind;
		sex = setSex;
	}

	Animal createAnimal() {
		std::string nameStorage;
		std::cout << "Введите кличку животного: " << std::endl;
		std::cin >> nameStorage;
		std::string kindStorage;
		std::cout << "Укажите вид животного: " << std::endl;
		std::cin >> kindStorage;
		std::string sexStorage;
		std::cout << "Укажите пол животного(м/ж): " << std::endl;
		std::cin >> sexStorage;
		std::string weightStorage;
		std::cout << "Укажите вес животного, кг: " << std::endl;
		std::cin >> weightStorage;
		Animal newAnimal(nameStorage, kindStorage, sexStorage, weightStorage);
		return newAnimal;
	}
	
	std::string getWeight() const {
		return weight;
	}

	std::string getName() const {
		return name;
	}
	
	std::string getKind() const {
		return kind;
	}

	void setWeight(std::string newWeight) {
		weight = newWeight;
	}
	void giveAnimalsInfo() const {
		std::cout << "Кличка: " << name;
		std::cout << "Вид: " << kind;
		std::cout << "Пол: " << sex;
		std::cout << "Вес, кг: " << weight;
		std::cout << "--------------------------------------\n";
	}
};

class House {
private:
	std::string adress;
	std::string phoneNumber;
	std::string howManyKidsLiving;
public:
	House() = default;             // конструктор по умолчанию
	House(std::string setAdress, std::string setPhoneNumber, std::string setLivingKids) {	//конструктор со всеми аргументами
		adress = setAdress;
		phoneNumber = setPhoneNumber;
		howManyKidsLiving = setLivingKids;
	}
	House createHouse() {
		std::string adressStorage;
		std::cout << "Введите адрес: " << std::endl;
		std::cin >> adressStorage;
		std::string phoneNumberStorage;
		std::cout << "Укажите номер телефона: " << std::endl;
		std::cin >> phoneNumberStorage;
		std::string howManyKidsLivingStorage;
		std::cout << "Укажите, сколько детей живут: " << std::endl;
		std::cin >> howManyKidsLivingStorage;
		House newHouse(adressStorage, phoneNumberStorage, howManyKidsLivingStorage);
		return newHouse;
	}

	std::string getPhoneNumber() const {
		return phoneNumber;
	}

	std::string getAdress() const {
		return adress;
	}
	
	std::string getKidsInfo() const {
		return howManyKidsLiving;
	}

	void setAdress(std::string newAdress) {
		adress = newAdress;
	}

	void setPhoneNumber(std::string newPhone) {
		phoneNumber = newPhone;
	}

	void setKidsAmount(std::string newHowManyKidsLiving) {
		howManyKidsLiving = newHowManyKidsLiving;
	}

	void giveHousesInfo() const {
		std::cout << "Адрес: " << adress;
		std::cout << "Номер телефона: " << phoneNumber;
		std::cout << "Детей живет в этом доме: " << howManyKidsLiving;
		std::cout << "--------------------------------------\n";
	}
};

class Owner {
private:
	std::string name;
	std::string birthday;
	std::string responsibilityDegree;
public:
	Owner() = default;            // конструктор по умолчанию
	Owner(std::string setName, std::string setBirthday, std::string setResponsibilityDegree) {	//конструктор со всеми аргументами
		name = setName;
		responsibilityDegree = setResponsibilityDegree;
		birthday = setBirthday;
	}
	Owner createOwner() {
		std::string nameStorage;
		std::cout << "Введите ФИО: " << std::endl;
		std::cin >> nameStorage;
		std::string birthdayStorage;
		std::cout << "Укажите дату рождения: " << std::endl;
		std::cin >> birthdayStorage;
		std::string degreeStorage;
		std::cout << "Укажите степень ответственности хозяина: " << std::endl;
		std::cin >> degreeStorage;
		Owner newOwner(nameStorage, birthdayStorage, degreeStorage);
		return newOwner;
	}
	std::string getName() const {
		return name;
	}

	std::string getBirthday() const {
		return birthday;
	}

	std::string getResponsibilityDegree() const {
		return responsibilityDegree;
	}

	void giveOwnersInfo() const {
		std::cout << "ФИО: " << name;
		std::cout << "Дата рождения: " << birthday;
		std::cout << "Степень ответственности: " << responsibilityDegree;
		std::cout << "--------------------------------------\n";
	}
};

class Residence {
private: 
	class House whereLiving;
	class Owner whoLiving;
public:
	Residence() = default;
	Residence(House setWhereLiving, Owner setWhoLiving) {
		whereLiving = setWhereLiving;
		whoLiving = setWhoLiving;
	}

	std::string getOwnerNameFromResidence() const {
		return whoLiving.getName();
	}

	House getHouseFromResidence() const {
		return whereLiving;
	}

	Owner getOwnerFromResidence() const {
		return whoLiving;
	}
}; 


class Pet {
private:
	class Animal AnimalInPet;
	class House HouseInPet;
	class Owner OwnerInPet;
public:
	Pet() = default;            // конструктор по умолчанию

	Pet(Animal setAnimal, House setHouse, Owner setOwner) {      //конструктор со всеми аргументами
		AnimalInPet = setAnimal;
		HouseInPet = setHouse;
		OwnerInPet = setOwner;
	}

	void givePetsInfo() {
		std::cout << "\nКличка: " << AnimalInPet.getName();
		std::cout << "Имя хозяина: " << OwnerInPet.getName();
		std::cout << "Номер телефона: " << HouseInPet.getPhoneNumber() << std::endl;
		std::cout << "--------------------------------\n";
	}

	void changePetOwner(Pet* changingPet, Owner newOwner) {
		std::cout << "\nКличка: " << (*changingPet).AnimalInPet.getName();
		std::cout << "Прежний хозяин: " << (*changingPet).OwnerInPet.getName();
		(*changingPet).OwnerInPet = newOwner;
		std::cout << "Новый хозяин: " << newOwner.getName() << std::endl;
	}
};

