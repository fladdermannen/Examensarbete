package com.example.absol.examensarbete;

public class VolleyFetcher {

    private final static String TAG = "myTag";

    VolleyFetcher(){

    }

    public String makeRequestString(int year, String gender){

        switch(gender){
            case "girls": {
                return "{\n" +
                        "  \"query\": [\n" +
                        "    {\n" +
                        "      \"code\": \"Tilltalsnamn\",\n" +
                        "      \"selection\": {\n" +
                        "        \"filter\": \"vs:Flickor100\",\n" +
                        "        \"values\": [\n" +
                        "          \"2Adele\",\n" +
                        "          \"2Agnes\",\n" +
                        "          \"2Alexandra\",\n" +
                        "          \"2Alice\",\n" +
                        "          \"2Alicia\",\n" +
                        "          \"2Alma\",\n" +
                        "          \"2Alva\",\n" +
                        "          \"2Amanda\",\n" +
                        "          \"2Amelia\",\n" +
                        "          \"2Andrea\",\n" +
                        "          \"2Angelica\",\n" +
                        "          \"2Angelina\",\n" +
                        "          \"2Anna\",\n" +
                        "          \"2Annie\",\n" +
                        "          \"2Astrid\",\n" +
                        "          \"2Beatrice\",\n" +
                        "          \"2Bianca\",\n" +
                        "          \"2Caroline\",\n" +
                        "          \"2Cassandra\",\n" +
                        "          \"2Cecilia\",\n" +
                        "          \"2Celine\",\n" +
                        "          \"2Cleo\",\n" +
                        "          \"2Cornelia\",\n" +
                        "          \"2Daniela\",\n" +
                        "          \"2Denise\",\n" +
                        "          \"2Ebba\",\n" +
                        "          \"2Edit\",\n" +
                        "          \"2Elin\",\n" +
                        "          \"2Elina\",\n" +
                        "          \"2Elisa\",\n" +
                        "          \"2Elise\",\n" +
                        "          \"2Ella\",\n" +
                        "          \"2Ellen\",\n" +
                        "          \"2Ellinor\",\n" +
                        "          \"2Elly\",\n" +
                        "          \"2Elsa\",\n" +
                        "          \"2Elvira\",\n" +
                        "          \"2Emelie\",\n" +
                        "          \"2Emilia\",\n" +
                        "          \"2Emma\",\n" +
                        "          \"2Emmy\",\n" +
                        "          \"2Engla\",\n" +
                        "          \"2Erika\",\n" +
                        "          \"2Ester\",\n" +
                        "          \"2Evelina\",\n" +
                        "          \"2Fanny\",\n" +
                        "          \"2Felicia\",\n" +
                        "          \"2Filippa\",\n" +
                        "          \"2Freja\",\n" +
                        "          \"2Frida\",\n" +
                        "          \"2Gabriella\",\n" +
                        "          \"2Greta\",\n" +
                        "          \"2Hanna\",\n" +
                        "          \"2Hedda\",\n" +
                        "          \"2Hedvig\",\n" +
                        "          \"2Hilda\",\n" +
                        "          \"2Hilma\",\n" +
                        "          \"2Ida\",\n" +
                        "          \"2Idun\",\n" +
                        "          \"2Inez\",\n" +
                        "          \"2Ingrid\",\n" +
                        "          \"2Iris\",\n" +
                        "          \"2Irma\",\n" +
                        "          \"2Isa\",\n" +
                        "          \"2Isabella\",\n" +
                        "          \"2Isabelle\",\n" +
                        "          \"2Jasmine\",\n" +
                        "          \"2Jennifer\",\n" +
                        "          \"2Jenny\",\n" +
                        "          \"2Jessica\",\n" +
                        "          \"2Johanna\",\n" +
                        "          \"2Joline\",\n" +
                        "          \"2Jonna\",\n" +
                        "          \"2Josefin\",\n" +
                        "          \"2Julia\",\n" +
                        "          \"2Julie\",\n" +
                        "          \"2Juni\",\n" +
                        "          \"2Kajsa\",\n" +
                        "          \"2Karin\",\n" +
                        "          \"2Karolina\",\n" +
                        "          \"2Klara\",\n" +
                        "          \"2Kristina\",\n" +
                        "          \"2Lea\",\n" +
                        "          \"2Leia\",\n" +
                        "          \"2Lilly\",\n" +
                        "          \"2Lina\",\n" +
                        "          \"2Linda\",\n" +
                        "          \"2Linn\",\n" +
                        "          \"2Linnéa\",\n" +
                        "          \"2Lisa\",\n" +
                        "          \"2Liv\",\n" +
                        "          \"2Livia\",\n" +
                        "          \"2Lo\",\n" +
                        "          \"2Louise\",\n" +
                        "          \"2Lova\",\n" +
                        "          \"2Lovis\",\n" +
                        "          \"2Lovisa\",\n" +
                        "          \"2Luna\",\n" +
                        "          \"2Lykke\",\n" +
                        "          \"2Madeleine\",\n" +
                        "          \"2Maja\",\n" +
                        "          \"2Majken\",\n" +
                        "          \"2Malin\",\n" +
                        "          \"2Malva\",\n" +
                        "          \"2Maria\",\n" +
                        "          \"2Mariam\",\n" +
                        "          \"2Matilda\",\n" +
                        "          \"2Meja\",\n" +
                        "          \"2Melina\",\n" +
                        "          \"2Melissa\",\n" +
                        "          \"2Michelle\",\n" +
                        "          \"2Mikaela\",\n" +
                        "          \"2Mila\",\n" +
                        "          \"2Minna\",\n" +
                        "          \"2Mira\",\n" +
                        "          \"2Miranda\",\n" +
                        "          \"2Moa\",\n" +
                        "          \"2Molly\",\n" +
                        "          \"2My\",\n" +
                        "          \"2Märta\",\n" +
                        "          \"2Nathalie\",\n" +
                        "          \"2Nellie\",\n" +
                        "          \"2Nicole\",\n" +
                        "          \"2Nina\",\n" +
                        "          \"2Noomi\",\n" +
                        "          \"2Nora\",\n" +
                        "          \"2Nova\",\n" +
                        "          \"2Novalie\",\n" +
                        "          \"2Olivia\",\n" +
                        "          \"2Patricia\",\n" +
                        "          \"2Paulina\",\n" +
                        "          \"2Penny\",\n" +
                        "          \"2Rebecka\",\n" +
                        "          \"2Ronja\",\n" +
                        "          \"2Rut\",\n" +
                        "          \"2Sabina\",\n" +
                        "          \"2Saga\",\n" +
                        "          \"2Sally\",\n" +
                        "          \"2Sandra\",\n" +
                        "          \"2Sanna\",\n" +
                        "          \"2Sara\",\n" +
                        "          \"2Selma\",\n" +
                        "          \"2Signe\",\n" +
                        "          \"2Sigrid\",\n" +
                        "          \"2Siri\",\n" +
                        "          \"2Smilla\",\n" +
                        "          \"2Sofia\",\n" +
                        "          \"2Sofie\",\n" +
                        "          \"2Stella\",\n" +
                        "          \"2Stina\",\n" +
                        "          \"2Svea\",\n" +
                        "          \"2Thea\",\n" +
                        "          \"2Therese\",\n" +
                        "          \"2Tilda\",\n" +
                        "          \"2Tilde\",\n" +
                        "          \"2Tindra\",\n" +
                        "          \"2Tova\",\n" +
                        "          \"2Tove\",\n" +
                        "          \"2Tuva\",\n" +
                        "          \"2Tyra\",\n" +
                        "          \"2Vanessa\",\n" +
                        "          \"2Vendela\",\n" +
                        "          \"2Vera\",\n" +
                        "          \"2Viktoria\",\n" +
                        "          \"2Vilda\",\n" +
                        "          \"2Wilma\",\n" +
                        "          \"2Zoe\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"code\": \"ContentsCode\",\n" +
                        "      \"selection\": {\n" +
                        "        \"filter\": \"item\",\n" +
                        "        \"values\": [\n" +
                        "          \"BE0001AJ\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"code\": \"Tid\",\n" +
                        "      \"selection\": {\n" +
                        "        \"filter\": \"item\",\n" +
                        "        \"values\": [\n" +
                        "          \"" + year + "\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"response\": {\n" +
                        "    \"format\": \"json\"\n" +
                        "  }\n" +
                        "}";
            }
            case "boys": {
                return "{\n" +
                        "  \"query\": [\n" +
                        "    {\n" +
                        "      \"code\": \"Tilltalsnamn\",\n" +
                        "      \"selection\": {\n" +
                        "        \"filter\": \"vs:Pojkar100\",\n" +
                        "        \"values\": [\n" +
                        "          \"1Adam\",\n" +
                        "          \"1Adrian\",\n" +
                        "          \"1Ahmed\",\n" +
                        "          \"1Albert\",\n" +
                        "          \"1Albin\",\n" +
                        "          \"1Alex\",\n" +
                        "          \"1Alexander\",\n" +
                        "          \"1Alfons\",\n" +
                        "          \"1Alfred\",\n" +
                        "          \"1Algot\",\n" +
                        "          \"1Ali\",\n" +
                        "          \"1Alvin\",\n" +
                        "          \"1Anders\",\n" +
                        "          \"1André\",\n" +
                        "          \"1Andreas\",\n" +
                        "          \"1Anton\",\n" +
                        "          \"1Aron\",\n" +
                        "          \"1Arvid\",\n" +
                        "          \"1August\",\n" +
                        "          \"1Axel\",\n" +
                        "          \"1Benjamin\",\n" +
                        "          \"1Björn\",\n" +
                        "          \"1Casper\",\n" +
                        "          \"1Charlie\",\n" +
                        "          \"1Christian\",\n" +
                        "          \"1Christoffer\",\n" +
                        "          \"1Colin\",\n" +
                        "          \"1Daniel\",\n" +
                        "          \"1Dante\",\n" +
                        "          \"1David\",\n" +
                        "          \"1Dennis\",\n" +
                        "          \"1Douglas\",\n" +
                        "          \"1Ebbe\",\n" +
                        "          \"1Eddie\",\n" +
                        "          \"1Edvard\",\n" +
                        "          \"1Edvin\",\n" +
                        "          \"1Elias\",\n" +
                        "          \"1Elis\",\n" +
                        "          \"1Elliot\",\n" +
                        "          \"1Elton\",\n" +
                        "          \"1Elvin\",\n" +
                        "          \"1Emanuel\",\n" +
                        "          \"1Emil\",\n" +
                        "          \"1Erik\",\n" +
                        "          \"1Fabian\",\n" +
                        "          \"1Felix\",\n" +
                        "          \"1Filip\",\n" +
                        "          \"1Folke\",\n" +
                        "          \"1Frank\",\n" +
                        "          \"1Frans\",\n" +
                        "          \"1Fredrik\",\n" +
                        "          \"1Gabriel\",\n" +
                        "          \"1Gustav\",\n" +
                        "          \"1Hampus\",\n" +
                        "          \"1Hannes\",\n" +
                        "          \"1Harry\",\n" +
                        "          \"1Henrik\",\n" +
                        "          \"1Henry\",\n" +
                        "          \"1Herman\",\n" +
                        "          \"1Hjalmar\",\n" +
                        "          \"1Hugo\",\n" +
                        "          \"1Isak\",\n" +
                        "          \"1Ivar\",\n" +
                        "          \"1Jack\",\n" +
                        "          \"1Jakob\",\n" +
                        "          \"1Jens\",\n" +
                        "          \"1Jesper\",\n" +
                        "          \"1Jimmy\",\n" +
                        "          \"1Joakim\",\n" +
                        "          \"1Joel\",\n" +
                        "          \"1Johan\",\n" +
                        "          \"1Johannes\",\n" +
                        "          \"1John\",\n" +
                        "          \"1Jonas\",\n" +
                        "          \"1Jonathan\",\n" +
                        "          \"1Josef\",\n" +
                        "          \"1Julian\",\n" +
                        "          \"1Julius\",\n" +
                        "          \"1Kalle\",\n" +
                        "          \"1Karl\",\n" +
                        "          \"1Kevin\",\n" +
                        "          \"1Kian\",\n" +
                        "          \"1Kim\",\n" +
                        "          \"1Leo\",\n" +
                        "          \"1Leon\",\n" +
                        "          \"1Levi\",\n" +
                        "          \"1Liam\",\n" +
                        "          \"1Linus\",\n" +
                        "          \"1Loke\",\n" +
                        "          \"1Louie\",\n" +
                        "          \"1Love\",\n" +
                        "          \"1Lucas\",\n" +
                        "          \"1Ludvig\",\n" +
                        "          \"1Magnus\",\n" +
                        "          \"1Malte\",\n" +
                        "          \"1Marcus\",\n" +
                        "          \"1Martin\",\n" +
                        "          \"1Matteo\",\n" +
                        "          \"1Mattias\",\n" +
                        "          \"1Max\",\n" +
                        "          \"1Maximilian\",\n" +
                        "          \"1Melker\",\n" +
                        "          \"1Melvin\",\n" +
                        "          \"1Mikael\",\n" +
                        "          \"1Milian\",\n" +
                        "          \"1Milo\",\n" +
                        "          \"1Milton\",\n" +
                        "          \"1Mio\",\n" +
                        "          \"1Mohamed\",\n" +
                        "          \"1Måns\",\n" +
                        "          \"1Neo\",\n" +
                        "          \"1Nicolas\",\n" +
                        "          \"1Niklas\",\n" +
                        "          \"1Nils\",\n" +
                        "          \"1Noah\",\n" +
                        "          \"1Noel\",\n" +
                        "          \"1Oliver\",\n" +
                        "          \"1Olle\",\n" +
                        "          \"1Olof\",\n" +
                        "          \"1Omar\",\n" +
                        "          \"1Oskar\",\n" +
                        "          \"1Otto\",\n" +
                        "          \"1Patrik\",\n" +
                        "          \"1Per\",\n" +
                        "          \"1Petter\",\n" +
                        "          \"1Pontus\",\n" +
                        "          \"1Rasmus\",\n" +
                        "          \"1Rickard\",\n" +
                        "          \"1Robert\",\n" +
                        "          \"1Robin\",\n" +
                        "          \"1Ruben\",\n" +
                        "          \"1Sam\",\n" +
                        "          \"1Samuel\",\n" +
                        "          \"1Sebastian\",\n" +
                        "          \"1Sigge\",\n" +
                        "          \"1Simon\",\n" +
                        "          \"1Sixten\",\n" +
                        "          \"1Stefan\",\n" +
                        "          \"1Svante\",\n" +
                        "          \"1Tage\",\n" +
                        "          \"1Theo\",\n" +
                        "          \"1Theodor\",\n" +
                        "          \"1Thomas\",\n" +
                        "          \"1Tim\",\n" +
                        "          \"1Tobias\",\n" +
                        "          \"1Tom\",\n" +
                        "          \"1Tor\",\n" +
                        "          \"1Ture\",\n" +
                        "          \"1Valter\",\n" +
                        "          \"1Vidar\",\n" +
                        "          \"1Vide\",\n" +
                        "          \"1Viggo\",\n" +
                        "          \"1Viktor\",\n" +
                        "          \"1Vilgot\",\n" +
                        "          \"1Vilhelm\",\n" +
                        "          \"1Ville\",\n" +
                        "          \"1William\",\n" +
                        "          \"1Wilmer\",\n" +
                        "          \"1Vincent\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"code\": \"ContentsCode\",\n" +
                        "      \"selection\": {\n" +
                        "        \"filter\": \"item\",\n" +
                        "        \"values\": [\n" +
                        "          \"BE0001AJ\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"code\": \"Tid\",\n" +
                        "      \"selection\": {\n" +
                        "        \"filter\": \"item\",\n" +
                        "        \"values\": [\n" +
                        "          \"" + year + "\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"response\": {\n" +
                        "    \"format\": \"json\"\n" +
                        "  }\n" +
                        "}";
            }

            default:
                return null;
        }
    }
}
