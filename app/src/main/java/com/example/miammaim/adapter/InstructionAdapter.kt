package com.example.miammaim.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.R
import com.example.miammaim.model.Ingredient

class InstructionRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public var instructionNameTextView: TextView = itemView.findViewById(R.id.meal_instruction_number)
    public var instructionMeasureTextView: TextView = itemView.findViewById(R.id.meal_instruction)
}

class InstructionAdapter(val instructions: List<String>) : RecyclerView.Adapter<InstructionRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meal_instruction_item, parent, false)
        return InstructionRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructionRecyclerViewHolder, position: Int) {
        holder.instructionNameTextView.setText("Step ${position + 1}")
        holder.instructionMeasureTextView.text = instructions[position]
        Log.d("OKHTTP", "onBindViewHolder: " + instructions[position])
    }

    override fun getItemCount(): Int {
        return instructions.count()
    }
}