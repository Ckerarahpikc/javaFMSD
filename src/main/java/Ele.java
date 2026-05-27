import java.util.Scanner;
import java.lang.StringBuilder;

public class Ele {
  private static final int ITEMS_COUNT = 8;

  private String nr;
  private String nume;
  private String prenume;
  private String adresa;
  private String telefon;
  private String profil;
  private int clasa;
  private double nota;

  public static void main(String[] args) {
    // System.out.println("The Class is working...");
    // Ele el = new Ele();
    //
    // int fullNameLength = el.getNameLength();
    // System.out.println("Name length: " + fullNameLength);
    // el.getPerson();
    //
    // or use getters

    // System.out
    // .println("\n" + el.getNr() + "\n" + el.getFName() + "\n" + el.getLName() +
    // "\n" + el.getAddress() + "\n"
    // + el.getPhone() + "\n" + el.getProfile() + "\n"
    // + el.getYear() + "\n" + el.getGrade());
  }

  public Ele(String nr, String nume, String prenume, String adresa, String telefon, String profil, int clasa,
      double nota) {
    setNr(nr);
    setFName(nume);
    setLName(prenume);
    setAddress(adresa);
    setPhone(telefon);
    setProfile(profil);
    setYear(clasa);
    setGrade(nota);
  }

  public Ele() {
    this("000.", "Nespecificat", "Nespecificat", "Nespecificat", "0000 00 000", "Uman", 0, 0.00);
  }

  // setter
  public void setNr(String nr) {
    if (nr == null || nr.trim().isEmpty()) {
      throw new Error("Invalid Expression.");
    }
    this.nr = nr;
  }

  public void setFName(String nume) {
    if (nume == null || nume.trim().isEmpty()) {
      throw new Error("Invalid Expression.");
    }
    this.nume = nume;
  }

  public void setLName(String prenume) {
    if (prenume == null || prenume.trim().isEmpty()) {
      throw new Error("Invalid Expression.");
    }
    this.prenume = prenume;
  }

  public void setAddress(String adresa) {
    if (adresa == null || adresa.trim().isEmpty()) {
      throw new Error("Invalid Expression.");
    }
    this.adresa = adresa;
  }

  public void setPhone(String telefon) {
    if (telefon == null || telefon.trim().isEmpty()) {
      throw new Error("Invalid Expression.");
    }
    this.telefon = telefon;
  }

  public void setProfile(String profil) {
    if (profil == null || profil.trim().isEmpty()) {
      throw new Error("Invalid Expression.");

    }
    this.profil = profil;
  }

  public void setYear(int clasa) {
    if (clasa <= 0 || clasa >= 13) {
      throw new Error("Invalid Expression.");

    }
    this.clasa = clasa;
  }

  public void setGrade(double nota) {
    if (nota <= 0 || nota >= 11) {
      throw new Error("Invalid Expression.");
    }
    this.nota = nota;
  }

  // getters
  final public Integer getItemsCount() {
    return ITEMS_COUNT;
  }

  public String getNr() {
    return nr;
  }

  public String getFName() {
    return nume;
  }

  public String getLName() {
    return prenume;
  }

  public String getAddress() {
    return adresa;
  }

  public String getPhone() {
    return telefon;
  }

  public String getProfile() {
    return profil;
  }

  public Integer getYear() {
    return clasa;
  }

  public Double getGrade() {
    return nota;
  }

  // method
  public Integer getNameLength() {
    return (nume + prenume).trim().length();
  }

  public void getPerson() {
    System.out.println("\n" + nr + "\n" + nume + "\n" + prenume + "\n" + adresa + "\n" + telefon + "\n" + profil + "\n"
        + clasa + "\n" + nota);
  }

  @Override
  public String toString() {
    return nume + " : " + nota;
  }
}
