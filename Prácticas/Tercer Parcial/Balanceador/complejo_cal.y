
%{
#include <stdio.h>
#include <stdlib.h>
#include "complejo_cal.h" //Llamamos a las operaciones de la calculadora compleja


void yyerror (char *s);
int yylex ();
void warning(char *s, char *t);

%}
%union {
	ComplejoAP val;
	double compo;
}
//
%token <compo> NUMBER
%type <val> complejoE complejo
%left '+' '-'
%left '*' '/'
%nonassoc '(' ')'

%%
//Definimos las operaciones de los complejos
list:   
	| list'\n'
        | list complejoE '\n'  {  imprimirC($2);}
	;
complejo: NUMBER '+' NUMBER 'i' {;$$=creaComplejo($1,$3);}
	| NUMBER '-' NUMBER 'i' {;$$=creaComplejo($1,-$3);}
	| '-' NUMBER '+' NUMBER 'i' {;$$=creaComplejo(-$2,$4);}
	| '-' NUMBER '-' NUMBER 'i' {;$$=creaComplejo(-$2,-$4);}
    ;

complejoE:   complejo
	     | complejoE '+' complejoE {$$ = Complejo_add($1,$3);}
             | complejoE '-' complejoE {$$ = Complejo_sub($1,$3);}
	     | complejoE '*' complejoE {$$ = Complejo_mul($1,$3);}
	     | complejoE '/' complejoE {$$ = Complejo_div($1,$3);}
	     | '(' complejoE ')' {$$ = $2;}
             ;

%%


#include <stdio.h>
#include <ctype.h>
char *progname;
int lineno = 1;

void main (int argc, char *argv[]){
	progname=argv[0];
  	yyparse ();
}
int yylex (){
  	int c;

  	while ((c = getchar ()) == ' ' || c == '\t')  
  		;
 	if (c == EOF)                            
    		return 0;
  	if (c == '.' || isdigit (c)) {
      		ungetc (c, stdin);
      		scanf ("%lf", &yylval.compo);
	      return NUMBER;
    	}
  	if(c == '\n')
		lineno++;
  	return c;                                
}
void yyerror (char *s) {
	warning(s, (char *) 0);
}
void warning(char *s, char *t){
	fprintf (stderr, "%s: %s", progname, s);
	if(t)
		fprintf (stderr, " %s", t);
	fprintf (stderr, "cerca de la linea %d\n", lineno);
}
Complejo *creaComplejo(int real, int img){
   Complejo *nvo = (Complejo*)malloc(sizeof(Complejo));
   nvo -> real = real;
   nvo -> img = img;
   return nvo;
}
Complejo *Complejo_add(Complejo *c1, Complejo *c2){
  return creaComplejo(c1->real + c2->real, c1->img + c2->img);
}
Complejo *Complejo_sub(Complejo *c1, Complejo *c2){
  return creaComplejo(c1->real - c2->real, c1->img + c2->img);
}
Complejo *Complejo_mul(Complejo *c1, Complejo *c2){
  return creaComplejo( c1->real*c2->real - c1->img*c2->img,
                       c1->img*c2->real + c1->real*c2->img);
}
Complejo *Complejo_div(Complejo *c1, Complejo *c2){
   double d = c2->real*c2->real + c2->img*c2->img;
   return creaComplejo( (c1->real*c2->real + c1->img*c2->img) / d,
                        (c1->img*c2->real - c1->real*c2->img) / d);
}
void imprimirC(Complejo *c){
   if(c->img != 0)
      printf("%f%+fi\n", c->real, c->img);
   else
      printf("%f\n", c->real);
}


