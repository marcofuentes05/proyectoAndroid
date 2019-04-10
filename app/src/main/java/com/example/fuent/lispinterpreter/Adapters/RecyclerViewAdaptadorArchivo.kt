package com.example.fuent.lispinterpreter.Adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fuent.lispinterpreter.Archivo
import com.example.fuent.lispinterpreter.Carpeta
import com.example.fuent.lispinterpreter.R

class RecyclerViewAdaptadorArchivo: RecyclerView.Adapter<RecyclerViewAdaptadorArchivo.ViewHolder>{

    override fun getItemCount(): Int {
        return listaArchivos.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder{
        var name: TextView = itemView.findViewById(R.id.code_name)
        var author : TextView = itemView.findViewById(R.id.code_author)
        var view : View

        constructor(v: View) : super(v) {
            view = v
        }
    }

    var listaArchivos : ArrayList<Archivo>

    constructor(codeList: ArrayList<Archivo>){
        this.listaArchivos = codeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_code,parent,false)
        return ViewHolder(view)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listaArchivos[position].nombre
        holder.author.text = listaArchivos[position].autor
    }
}