package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo _primero;
    private Nodo _ultimo; 
    private int _longitud;

    private class Nodo {
        T valor;
        Nodo ant;
        Nodo sig;

        Nodo(T v) { valor = v; } 
    }

    public ListaEnlazada() {
        _primero = null;
        _ultimo = null;
        _longitud = 0;
    }

    public int longitud() {
        return _longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if(_longitud==0){
            _primero = nuevo;
            _ultimo = nuevo;
        }else{
            nuevo.sig = _primero; //el sig del nuevo es el ultimo
            nuevo.ant = null;
            _primero.ant = nuevo; //el anterior del nuevo es el siguiente
            _primero = nuevo;
        }

        _longitud++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if(_longitud==0){
            _primero = nuevo;
            _ultimo = nuevo;
        }else{
            nuevo.ant = _ultimo; //el sig del nuevo es el ultimo
            nuevo.sig = null;
            _ultimo.sig = nuevo; //el anterior del nuevo es el siguiente
            _ultimo = nuevo;
        }

        _longitud++;
    }

    public T obtener(int i) {
        Nodo actual = _primero;
        for(int j=0;j<i;j++){
            actual = actual.sig;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        Nodo actual = _primero;
        Nodo post = actual.sig;
        Nodo prev = actual.ant;
        if(_longitud==1){
            _primero=null;
            _ultimo=null;
        }else{
            for(int j=0;j<i;j++){
                prev = actual;
                actual = post;
                post = actual.sig;
            }
            if(i==0){
                _primero = post;
            }
            else if(i==_longitud-1){
                _ultimo = prev;
            }
            else{
                post.ant = prev;
                prev.sig = post;
            }
        }
        _longitud--;

    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = _primero;
        for(int j=0;j<indice;j++){
            actual = actual.sig;
        }
        actual.valor = elem;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada copia = new ListaEnlazada();
        for(int i=0;i<_longitud;i++){
            copia.agregarAtras(obtener(i));
        }
        return copia;

    }

    public ListaEnlazada(ListaEnlazada<T> lista){
        for(int i=0;i<lista._longitud;i++){
            T elemento = lista.obtener(i);
            agregarAtras(elemento);
        }
        //otra forma.
        //ListaEnlazada<T> copia = lista.copiar();
        //_primero = copia._primero;
        //_ultimo = copia._ultimo;
        //_longitud = copia._longitud;
    }
    
    @Override
    public String toString() {//solamente con iteradores
        StringBuffer sBuffer = new StringBuffer(); //declaramos buffer
        sBuffer.append("[");//le agregamos un [
        
        Iterador<T> iterador = iterador(); //inicializamos iterador
        while (iterador.haySiguiente()) { 
            //itero sobre el iterador y uso la clase privada para obtener los elementos y seguir ciclando 
            T elemento = iterador.siguiente();
            sBuffer.append(elemento.toString());
            if (iterador.haySiguiente()) {
                sBuffer.append(", ");
            }
        }
        
        sBuffer.append("]"); //agrego un ] más
        return sBuffer.toString(); //return del buffer pasado a formato string
    }
    
    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        int it;
        ListaIterador(){
            it = 0;
        }

        public boolean haySiguiente() {
	        return it<_longitud;
        }
        
        public boolean hayAnterior() {
	        return it>0;
        }

        public T siguiente() {
	        int i = it;
            it = it + 1;
            return obtener(i);
        }
        

        public T anterior() {
            it =  it - 1;
            return obtener(it);
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

}
