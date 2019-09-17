# Flow2Week1

Spørgsmål i tirsdagsopgaven:

Jeg har ikke svaret på alting skriftligt, men her er de mest interessant sppørgsmål/svar.

One-to-one bidirectional
Run the project and investigate the generated tables (the foreign key). Is there any difference compared to the previous exercise. If not explain why.

Man ender med samme resultat, at CUSTOMER tabellen har en FK der hedder 'ADDRESS_ID', fordi vi valgte at CUSTOMER skulle være owneren.

One-to-many bidirectional

Observe the generated code, especially where we find the mappedBy value. Explain

Syntes jeg fandt en nogenlunde forklaring her: https://stackoverflow.com/questions/10968536/jpa-difference-in-the-use-of-the-mappedby-property-to-define-the-owning-entity

The following rules apply to bidirectional relationships:

The inverse side of a bidirectional relationship must refer to its owning side by use of the mappedBy element of the OneToOne, OneToMany, or ManyToMany annotation. The mappedBy element designates the property or field in the entity that is the owner of the relationship.

The many side of one-to-many / many-to-one bidirectional relationships must be the owning side, hence the mappedBy element cannot be specified on the ManyToOne annotation.

Run the project and investigate the generated tables (the foreign key). 

To tabeller bliver oprettet, FK ligger i ADDRESS.

Create a "test" method and insert a number of Customers with Addresses into the tables, using JPA. Which extra step is required for this strategy compared to OneToMany unidirectional?

I Address klassen skulle man lave en constructor der tog imod en customer også.

Many-to-many bidirectional

How can we implement ManyToMany relationships in an OO-language like Java?

Begge klasser kan have en liste af objekter af den anden klasse.

How can we implement ManyToMany relationships in a Relational Database?

Med en joined tabel der har PK fra de to tabeller man kigger på.

Run the project and investigate the generated tables. Explain ALL generated tables.

Der er en ADDRESS og en CUSTOMER tabel som før, og så er der en CUSTOMER_ADDRESS tabel der indeholder FK's til de to andre tabellers PK's.



