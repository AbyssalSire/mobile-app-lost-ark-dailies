# Mobile App Lost Ark Dailies

Mobile app created for the Mobile App Development Course in my Software Engineering major at Universidade Tecnológica Federal do Paraná (UTFPR)(Technological Federal University of Parana).

## Project Requirements
* The topic must be unique among the students in the class;
* The application must persist the data locally, and must not rely on network access to function;
* At least two distinct entities must persist, with a relationship between the entities (use of foreign key);
* The application is for personal, non-corporate use, and must not provide for communication with other software or user account profiles;
* As much as the application is limited, it must be functional and help the user with the problem it addresses;
* As it is an application for Smartphone, it is imagined that the routine of use of this software will preferably be with the user distant and without access to a computer (either desktop or notebook).
* The topic will only be valid after the professor's acceptance.

## Theme
Farm activities registry in Lost Ark. The game requires resource farming to improve the character's equipment, while activities with such resources have a maximum daily or weekly limit that each character can perform.

In the Korean version of the game there is a system that shows the user what each character has done, while the version we have this feature is not available, so I believe it would be a useful app for Lost Ark players.

The main entities to be persistent would be the characters and the activities performed.

Example:

Character 1 -> Performed 1/2 Guardian Raids in the day, Performed 2/2 Chaos Dungeons in the day, Performed the weekly Abyssal Dungeons entries

Character 2 -> Performed 2/2 Guardian raids in the day, Performed 0/2 Chaos Dungeons in the day, Did not try the weekly entries in the Abyssal Dungeons

We would have the first data table being the characters and the activities carried out, with the possibility of the user reseting each individually or reseting the dailies and weeklies he chooses, as well as adding new characters

## Expected Functionalities of the first delivery

* Specify the theme that the application is about. This theme must have been launched by the student in the "Project Theme" questionnaire, and it must be previously approved by the teacher. Repeated topics within the class will not be accepted.
* Register data entered by the user, which are related to the proposed business rule for the application (In this version, the data can only be kept in memory);
* Create at least one Entity Class to be manipulated within the application;
* Create at least one Activity that allows data manipulation (Insertion, Change and Removal);
* Create at least one Activity that lists the items registered in memory;
* Use a Custom Adapter in the Activity that lists the items;
* Create an Activity where information about what the application does, and the data authored by it is displayed;
* Use Options Menus, where actions appear with icons in the Activity's action bar;
* Use Contextual Action Menu, where actions appear with icons in the Activity's action bar;
* Use Up buttons on the secondary Activities action bar, to facilitate the user's return to the Activities specified as their parents;
* Provide some configuration or customization possibilities for the application, and persist this information using SharedPreferences;
* The Application must support two languages, English as standard and Brazilian Portuguese as optional.


## Grade: 9.2/10
### Professor's comments:
>Ao abrir o Menu de Contexto Flutuante e selecionar para editar um item, apenas abre para edição o primeiro elemento da listagem, e ao escolher excluir é apagado sempre o primeiro elemento da listagem.
>
>When opening the Floating Context Menu and selecting to edit an item, it opens only for the first element in the list, and when choosing the excluded element, the first element in the list is always excluded.


### Knows problems/bugs
* When opening the Floating Context Menu and selecting to edit an item, it opens only for the first element in the list, and when choosing the excluded element, the first element in the list is always excluded.
* When creating a character, the class is not being displayed in the character list activity, instead it is only showing "TextField"
