package application;

public class UI {
    // Função para deixar a String formatada como um título
    public static void title(String title){
        System.out.println("----------" + title.toUpperCase() + "----------");
    }
    // Esse não é o melhor método para limpar a tela, mas é oque deu pra fazer =)
    public static void clearScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }

    }
}
