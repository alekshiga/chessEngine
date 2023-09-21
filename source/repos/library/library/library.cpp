#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include "Header.h"
#include <windows.h>

std::vector<Pet> Pets;
std::vector<Owner> Owners;
std::vector<House> Houses;
std::vector<Animal> Animals;

void appendPetsInfo(Owner petOwner, Animal animal, House house) {
    Pet bufferPet;
    bufferPet.Animal = animal;
    bufferPet.House = house;
    bufferPet.Owner = petOwner;
    Pets.push_back(bufferPet);
}

int main()
{
    setlocale(LC_ALL, "Rus");
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    const char* name = "data.txt";
    FILE* file = fopen(name, "r");
    if (file == NULL) throw std::runtime_error("Файл не был открыт!");
    else {
        char* str = (char*)calloc(50, sizeof(char));
        fgets(str, 50, file);
        int count = int(*str) - 48;
        for (int i = 0; i < count; ++i) {
            Owner BufferPerson;
            fgets(str, 50, file);
            BufferPerson.name = str;
            fgets(str, 50, file);
            BufferPerson.birthDate = str;
            Owners.push_back(BufferPerson);
            House BufferHome;
            fgets(str, 50, file);
            BufferHome.adress = str;
            fgets(str, 50, file);
            BufferHome.phoneNumber = str;
            Houses.push_back(BufferHome);
            Animal BufferAnimal;
            fgets(str, 50, file);
            BufferAnimal.name = str;
            fgets(str, 50, file);
            BufferAnimal.weight = atoi(str);
            Animals.push_back(BufferAnimal);
            appendPetsInfo(BufferPerson, BufferAnimal, BufferHome);
        }
        printInfo(Pets);        // функция выводит общую информацию о всех питомцах
        std::string search;
        std::cout << "\nВведите кличку животного, чтобы узнать номер его хозяина" << std::endl;
        std::cin >> search;
        std::cout << PetFound(search, Pets) << std::endl;   // функция позволяет по кличке узнать номер телефона хозяина(вдруг питомец потерялся и был найден кем-то)
    }
}
