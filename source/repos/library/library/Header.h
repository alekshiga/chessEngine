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
	Animal(std::string setName, std::string setKind, std::string setSex, std::string setWeight) {	//����������� �� ����� �����������
		name = setName;
		weight = setWeight;
		kind = setKind;
		sex = setSex;
	}

	Animal createAnimal() {
		std::string nameStorage;
		std::cout << "������� ������ ���������: " << std::endl;
		std::cin >> nameStorage;
		std::string kindStorage;
		std::cout << "������� ��� ���������: " << std::endl;
		std::cin >> kindStorage;
		std::string sexStorage;
		std::cout << "������� ��� ���������(�/�): " << std::endl;
		std::cin >> sexStorage;
		std::string weightStorage;
		std::cout << "������� ��� ���������, ��: " << std::endl;
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
		std::cout << "������: " << name;
		std::cout << "���: " << kind;
		std::cout << "���: " << sex;
		std::cout << "���, ��: " << weight;
		std::cout << "--------------------------------------\n";
	}
};

class House {
private:
	std::string adress;
	std::string phoneNumber;
	std::string howManyKidsLiving;
public:
	House() = default;             // ����������� �� ���������
	House(std::string setAdress, std::string setPhoneNumber, std::string setLivingKids) {	//����������� �� ����� �����������
		adress = setAdress;
		phoneNumber = setPhoneNumber;
		howManyKidsLiving = setLivingKids;
	}
	House createHouse() {
		std::string adressStorage;
		std::cout << "������� �����: " << std::endl;
		std::cin >> adressStorage;
		std::string phoneNumberStorage;
		std::cout << "������� ����� ��������: " << std::endl;
		std::cin >> phoneNumberStorage;
		std::string howManyKidsLivingStorage;
		std::cout << "�������, ������� ����� �����: " << std::endl;
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
		std::cout << "�����: " << adress;
		std::cout << "����� ��������: " << phoneNumber;
		std::cout << "����� ����� � ���� ����: " << howManyKidsLiving;
		std::cout << "--------------------------------------\n";
	}
};

class Owner {
private:
	std::string name;
	std::string birthday;
	std::string responsibilityDegree;
public:
	Owner() = default;            // ����������� �� ���������
	Owner(std::string setName, std::string setBirthday, std::string setResponsibilityDegree) {	//����������� �� ����� �����������
		name = setName;
		responsibilityDegree = setResponsibilityDegree;
		birthday = setBirthday;
	}
	Owner createOwner() {
		std::string nameStorage;
		std::cout << "������� ���: " << std::endl;
		std::cin >> nameStorage;
		std::string birthdayStorage;
		std::cout << "������� ���� ��������: " << std::endl;
		std::cin >> birthdayStorage;
		std::string degreeStorage;
		std::cout << "������� ������� ��������������� �������: " << std::endl;
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
		std::cout << "���: " << name;
		std::cout << "���� ��������: " << birthday;
		std::cout << "������� ���������������: " << responsibilityDegree;
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
	Pet() = default;            // ����������� �� ���������

	Pet(Animal setAnimal, House setHouse, Owner setOwner) {      //����������� �� ����� �����������
		AnimalInPet = setAnimal;
		HouseInPet = setHouse;
		OwnerInPet = setOwner;
	}

	void givePetsInfo() {
		std::cout << "\n������: " << AnimalInPet.getName();
		std::cout << "��� �������: " << OwnerInPet.getName();
		std::cout << "����� ��������: " << HouseInPet.getPhoneNumber() << std::endl;
		std::cout << "--------------------------------\n";
	}

	void changePetOwner(Pet* changingPet, Owner newOwner) {
		std::cout << "\n������: " << (*changingPet).AnimalInPet.getName();
		std::cout << "������� ������: " << (*changingPet).OwnerInPet.getName();
		(*changingPet).OwnerInPet = newOwner;
		std::cout << "����� ������: " << newOwner.getName() << std::endl;
	}
};

