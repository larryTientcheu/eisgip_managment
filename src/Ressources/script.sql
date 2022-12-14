CREATE TABLE addresse (id_address INTEGER NOT NULL, email VARCHAR(255), location VARCHAR(255), numero VARCHAR(255), PRIMARY KEY (id_address))
CREATE TABLE proffesseur (id_proff INTEGER NOT NULL, email VARCHAR(255), nom VARCHAR(255), specialite VARCHAR(255), universite VARCHAR(255), id_address INTEGER, id_niveaux INTEGER, PRIMARY KEY (id_proff))
CREATE TABLE niveau_2 (id_niveaux INTEGER NOT NULL, nbre_heure INTEGER, PRIMARY KEY (id_niveaux))
CREATE TABLE staff (id_staff INTEGER NOT NULL, email VARCHAR(255), nom VARCHAR(255), poste VARCHAR(255), id_address INTEGER, PRIMARY KEY (id_staff))
CREATE TABLE etudiant (matricule VARCHAR(255) NOT NULL, age INTEGER, nom VARCHAR(255), prenom VARCHAR(255), id_niveaux INTEGER, PRIMARY KEY (matricule))
CREATE TABLE niveau_1 (id_niveaux INTEGER NOT NULL, nbre_heur INTEGER, PRIMARY KEY (id_niveaux))
CREATE TABLE notes (notes_id INTEGER NOT NULL, cc BIGINT, examen BIGINT, semestre INTEGER, codes VARCHAR(255), matricule VARCHAR(255), PRIMARY KEY (notes_id))
CREATE TABLE niveaux (id_niveaux INTEGER NOT NULL, nbre_cours INTEGER, nbre_etud INTEGER, nbre_prof INTEGER, PRIMARY KEY (id_niveaux))
CREATE TABLE cours (codes VARCHAR(255) NOT NULL, nom VARCHAR(255), id_niveaux INTEGER, PRIMARY KEY (codes))
CREATE TABLE anee (id_anee VARCHAR(255) NOT NULL, PRIMARY KEY (id_anee))
CREATE TABLE annee_notes (id_anee VARCHAR(255) NOT NULL, semestre INTEGER NOT NULL, PRIMARY KEY (id_anee, semestre))
CREATE TABLE etudiant_address (id_address INTEGER NOT NULL, matricule VARCHAR(255) NOT NULL, PRIMARY KEY (id_address, matricule))
CREATE TABLE prof_cours (id_proff INTEGER NOT NULL, codes VARCHAR(255) NOT NULL, PRIMARY KEY (id_proff, codes))
CREATE TABLE etudiant_cours (matricule VARCHAR(255) NOT NULL, codes VARCHAR(255) NOT NULL, PRIMARY KEY (matricule, codes))
ALTER TABLE proffesseur ADD CONSTRAINT FK_proffesseur_id_niveaux FOREIGN KEY (id_niveaux) REFERENCES niveaux (id_niveaux)
ALTER TABLE proffesseur ADD CONSTRAINT FK_proffesseur_id_address FOREIGN KEY (id_address) REFERENCES addresse (id_address)
ALTER TABLE niveau_2 ADD CONSTRAINT FK_niveau_2_id_niveaux FOREIGN KEY (id_niveaux) REFERENCES niveaux (id_niveaux)
ALTER TABLE staff ADD CONSTRAINT FK_staff_id_address FOREIGN KEY (id_address) REFERENCES addresse (id_address)
ALTER TABLE etudiant ADD CONSTRAINT FK_etudiant_id_niveaux FOREIGN KEY (id_niveaux) REFERENCES niveaux (id_niveaux)
ALTER TABLE niveau_1 ADD CONSTRAINT FK_niveau_1_id_niveaux FOREIGN KEY (id_niveaux) REFERENCES niveaux (id_niveaux)
ALTER TABLE notes ADD CONSTRAINT FK_notes_codes FOREIGN KEY (codes) REFERENCES cours (codes)
ALTER TABLE notes ADD CONSTRAINT FK_notes_matricule FOREIGN KEY (matricule) REFERENCES etudiant (matricule)
ALTER TABLE cours ADD CONSTRAINT FK_cours_id_niveaux FOREIGN KEY (id_niveaux) REFERENCES niveaux (id_niveaux)
ALTER TABLE etudiant_address ADD CONSTRAINT FK_etudiant_address_id_address FOREIGN KEY (id_address) REFERENCES addresse (id_address)
ALTER TABLE etudiant_address ADD CONSTRAINT FK_etudiant_address_matricule FOREIGN KEY (matricule) REFERENCES etudiant (matricule)
ALTER TABLE prof_cours ADD CONSTRAINT FK_prof_cours_id_proff FOREIGN KEY (id_proff) REFERENCES proffesseur (id_proff)
ALTER TABLE prof_cours ADD CONSTRAINT FK_prof_cours_codes FOREIGN KEY (codes) REFERENCES cours (codes)
ALTER TABLE etudiant_cours ADD CONSTRAINT FK_etudiant_cours_matricule FOREIGN KEY (matricule) REFERENCES etudiant (matricule)
ALTER TABLE etudiant_cours ADD CONSTRAINT FK_etudiant_cours_codes FOREIGN KEY (codes) REFERENCES cours (codes)
