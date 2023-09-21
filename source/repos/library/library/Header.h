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
		if (pet.Animal.name == (name + "\n")) return "Номер хозяина: " + pet.House.phoneNumber;
	}
	return "В списке нет питомца с такой кличкой";
}

void printInfo(std::vector<Pet> pets) {
	std::cout << "Информация о питомцах\n-----------------------------------------------------" << std::endl;
	for (auto & pet : pets) {
		std::cout << "Кличка: " << pet.Animal.name;
		std::cout << "Хозяин: " << pet.Owner.name;
		std::cout << "Номер хозяина: " << pet.House.phoneNumber;
		std::cout << "Адрес: " << pet.House.adress;
		std::cout << "------------------------------------------------------\n";
	}
}

