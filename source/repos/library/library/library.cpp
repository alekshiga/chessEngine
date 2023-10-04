#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include "Header.h"
#include <windows.h>
#include <algorithm>
#include <conio.h>

std::vector<Owner> Owners;
std::vector<Animal> Animals;
std::vector<House> Houses;
std::vector<Residence> Residences;
std::vector<Pet> Pets;

int findMinLength(std::vector<Owner> Owners, std::vector<Animal> Animals, std::vector<House> Houses) {
    int min = Animals.size();
    if (min > Owners.size()) min = Owners.size();
    if (min > Houses.size()) min = Houses.size();
    return min;
}

int main()
{
    setlocale(LC_ALL, "Rus");
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    const char* animalsfile = "animals.txt";
    const char* ownersfile = "owners.txt";
    const char* housesfile = "houses.txt";
    FILE* animalsstream = fopen(animalsfile, "r");
    FILE* ownersstream = fopen(ownersfile, "r");
    FILE* housesstream = fopen(housesfile, "r");
    if (animalsstream == NULL || ownersstream == NULL || housesstream == NULL) throw std::runtime_error("File Open Error");
    char* str = (char*)calloc(50, sizeof(char));
    
    // заполняем векторы животных, хозяев, домов из соответствующих файлов

    for (int i = 0; fgets(str, 30, ownersstream) != NULL; ++i) {
        std::string ownerName = str;
        fgets(str, 15, ownersstream);
        std::string birthday = str;
        fgets(str, 3, ownersstream);
        std::string responsibilityDegree = str;
        Owner createOwnerRecord(ownerName, birthday, responsibilityDegree);
        Owners.push_back(createOwnerRecord);
    }
    fclose(ownersstream);

    for (int i = 0; fgets(str, 50, housesstream) != NULL; ++i) {
        std::string houseAdress = str;
        fgets(str, 15, housesstream);
        std::string phoneNumber = str;
        fgets(str, 3, housesstream);
        std::string howManyKidsLiving = str;
        House createHouseRecord(houseAdress, phoneNumber, howManyKidsLiving);
        Houses.push_back(createHouseRecord);
    }
    fclose(housesstream);

    for (int i = 0; fgets(str, 20, animalsstream) != NULL; ++i) {
        std::string petName = str;
        fgets(str, 20, animalsstream);
        std::string petKind = str;
        fgets(str, 3, animalsstream);
        std::string petSex = str;
        fgets(str, 5, animalsstream);
        std::string petWeight = str;
        Animal createAnimalRecord(petName, petKind, petSex, petWeight);
        Animals.push_back(createAnimalRecord);
    }
    fclose(animalsstream);

    int userChoiceOfOwner;

    // связываем дом и жителя в класс резиденция

    for (int i = 0; i < Owners.size(); i++) {
        std::cout << "Укажите, по какому адресу живёт " << Owners[i].getName();
        for (int j = 0; j < Houses.size(); j++) {
            std::cout << j + 1 << ". " << Houses[j].getAdress();
        }
        std::cin >> userChoiceOfOwner;
        Residence beforeAppendToVector(Houses[userChoiceOfOwner - 1], Owners[i]);
        Residences.push_back(beforeAppendToVector);
    }

    std::cout << "Информация о всех хозяевах\n--------------------------------------\n";
    for (int i = 0; i < Owners.size(); ++i) {
        std::cout << i + 1 << ". ";
        Owners[i].giveOwnersInfo();
    }
    std::cout << "\nИнформация о всех животных\n--------------------------------------\n";
    for (int i = 0; i < Animals.size(); ++i) {
        std::cout << i + 1 << ". ";
        Animals[i].giveAnimalsInfo();
    }
    std::cout << "\nИнформация о всех домах\n--------------------------------------\n";
    for (int i = 0; i < Houses.size(); ++i) {
        std::cout << i + 1 << ". ";
        Houses[i].giveHousesInfo();
    }
    int userChoiseOfAnimal;
    std::cout << "\nУкажите номер хозяина из списка, который хочет найти себе питомца: ";
    std::cin >> userChoiceOfOwner;
    std::cout << "Укажите номер животного из списка: ";
    std::cin >> userChoiseOfAnimal;
    char f;

    // создаем пару животное - резиденция

    do {
        std::cout << "\nСоздать пару " << Owners[userChoiceOfOwner - 1].getName() << "----------\n" << Animals[userChoiseOfAnimal - 1].getName() << Animals[userChoiseOfAnimal - 1].getKind();
        std::cout << "Enter - да / Esc - нет\n" << std::endl;
        f = _getch();
    } while (f != 13 && f != 27);
    if (f == 13) {
        std::string findResidenceByName = Owners[userChoiceOfOwner - 1].getName();
        for (int i = 0; i < Residences.size(); i++) {
            if (findResidenceByName == Residences[i].getOwnerNameFromResidence()) {
                Pet beforeAppendToVector(Animals[userChoiseOfAnimal - 1], Residences[i].getHouseFromResidence(), Residences[i].getOwnerFromResidence());
                Pets.push_back(beforeAppendToVector);
            }
        }
    }

    // например, поменяем хозяина у какого-нибудь животного, у которого уже есть хозяин

    int userChoiceOfPet;
    for (int i = 0; i < Owners.size(); ++i) {
        std::cout << i + 1 << ".";
        Owners[i].giveOwnersInfo();
    }

    for (int i = 0; i < Pets.size(); ++i) {
        std::cout << i + 1 << ".";
        Pets[i].givePetsInfo();
    }

    std::cout << "Введите номер питомца, у которого хотите поменять хозяина: ";
    std::cin >> userChoiceOfPet;
    std::cout << "Введите номер нового хозяина: ";
    std::cin >> userChoiceOfOwner;
    Pets[0].changePetOwner(&(Pets[userChoiceOfPet - 1]), Owners[userChoiceOfOwner - 1]);
    for (int i = 0; i < Pets.size(); ++i) {
        std::cout << i + 1 << ".";
        Pets[i].givePetsInfo();
    }
}
