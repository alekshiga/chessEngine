#pragma once
#include <string>
#include <vector>

struct Animal {
	std::string name;
	int weight;
} AnimalExample;

struct House {
	std::string adress;
	std::string phoneNumber;
} HouseExample;

struct Owner {
	std::string name;
	std::string birthDate;
} OwnerExample;

struct Pet {
	struct Animal Animal;
	struct House House;
	struct Owner Owner;
};

std::string PetFound(std::string name, std::vector<Pet> list) {
	for (const auto& pet : list) {
		if (pet.Animal.name == (name + "\n")) return "����� �������: " + pet.House.phoneNumber;
	}
	return "� ������ ��� ������� � ����� �������";
}

void printInfo(std::vector<Pet> pets) {
	std::cout << "���������� � ��������\n-----------------------------------------------------" << std::endl;
	for (auto & pet : pets) {
		std::cout << "������: " << pet.Animal.name;
		std::cout << "������: " << pet.Owner.name;
		std::cout << "����� �������: " << pet.House.phoneNumber;
		std::cout << "�����: " << pet.House.adress;
		std::cout << "------------------------------------------------------\n";
	}
}

