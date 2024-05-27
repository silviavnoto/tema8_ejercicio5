package tema8_ejercicio5;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.sql.*;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Font;

public class Tema8_ejercicio5 {

	private JFrame frame;
	private JTable table;
	private JTextField codigotextField;
	private JTextField preciotextField;
	private JTextField nombretextField;
	private JTextField unidadestextField;
	private JTextField nuevoPreciotextField;
	private JTextField unidadesAdquiridastextField;
	private JTextField unidadesaVendertextField;
	private double gananciasTotales = 0.0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tema8_ejercicio5 window = new Tema8_ejercicio5();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public Tema8_ejercicio5() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private double getPrecioProducto(int idProducto) throws SQLException {
	    Connection con = ConnectionSingleton.getConnection();
	    PreparedStatement pstmt = con.prepareStatement("SELECT precio FROM producto WHERE idproducto = ?");
	    pstmt.setInt(1, idProducto);
	    ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
	        return rs.getDouble("precio");
	    } else {
	        return 0.0; // Si el producto no se encuentra, se devuelve 0.0
	    }
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 928, 612);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// TABLE
		DefaultTableModel model = new DefaultTableModel();

		model.addColumn("ID");
		model.addColumn("Nombre");
		model.addColumn("Precio");
		model.addColumn("Unidades");

		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM producto");
			model.setRowCount(0);
			while (rs.next()) {
				Object[] row = new Object[4];
				row[0] = rs.getInt("idproducto");
				row[1] = rs.getString("nombre");
				row[2] = rs.getInt("precio");
				row[3] = rs.getInt("unidades");
				model.addRow(row);
			}

			table = new JTable(model);
			table.setEnabled(false);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.setBounds(43, 59, 383, 173);

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(30, 60, 329, 117);
			frame.getContentPane().add(scrollPane);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// BOTON MOSTRAR
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
			            Connection con = ConnectionSingleton.getConnection();
			            Statement stmt = con.createStatement();
			            ResultSet rs = stmt.executeQuery("SELECT * FROM producto");
			            model.setRowCount(0);
			            while (rs.next()) {
			                Object[] row = new Object[4];
			                row[0] = rs.getInt("idproducto");
			                row[1] = rs.getString("nombre");
			                row[2] = rs.getInt("precio");
			                row[3] = rs.getInt("unidades");
			                model.addRow(row);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }

			}
		});
		btnMostrar.setBounds(129, 12, 117, 25);
		frame.getContentPane().add(btnMostrar);
		
		btnMostrar.doClick(); 

		//NUEVO PRODUCTO
		codigotextField = new JTextField();
		codigotextField.setBounds(132, 243, 114, 19);
		frame.getContentPane().add(codigotextField);
		codigotextField.setColumns(10);
		
		nombretextField = new JTextField();
		nombretextField.setBounds(132, 268, 114, 19);
		frame.getContentPane().add(nombretextField);
		nombretextField.setColumns(10);
		
		preciotextField = new JTextField();
		preciotextField.setBounds(132, 300, 114, 19);
		frame.getContentPane().add(preciotextField);
		preciotextField.setColumns(10);
		
		unidadestextField = new JTextField();
		unidadestextField.setBounds(132, 329, 114, 19);
		frame.getContentPane().add(unidadestextField);
		unidadestextField.setColumns(10);
		
		
		
		JButton agregarbtnNewButton = new JButton("Añadir");
		agregarbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				Connection con = ConnectionSingleton.getConnection();
				PreparedStatement ins_pstmt = con.prepareStatement("INSERT INTO producto (idproducto, nombre, precio, unidades) VALUES (?, ?, ?, ?)");
					
				 	int idproducto = Integer.parseInt(codigotextField.getText());
		            String nombre = nombretextField.getText();
		            double precio = Double.parseDouble(preciotextField.getText());
		            int unidades = Integer.parseInt(unidadestextField.getText());
				
						ins_pstmt.setInt(1, idproducto); 
						ins_pstmt.setString(2, nombre); 
						ins_pstmt.setDouble(3, precio);
						ins_pstmt.setInt(4, unidades); 
						
						int rowsInserted = ins_pstmt.executeUpdate();
						ins_pstmt.close();
						JOptionPane.showMessageDialog(null, "Producto añadido"); //Caso OK
						
						btnMostrar.doClick(); 
						
			}catch (SQLException ex) {
				
				ex.printStackTrace();

				 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
			
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
	            JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
           
	        }
			}
		});
		agregarbtnNewButton.setBounds(69, 360, 117, 25);
		frame.getContentPane().add(agregarbtnNewButton);
		
		
		
		JLabel lblNewLabel_5 = new JLabel("Borrar Producto:");
		lblNewLabel_5.setBounds(30, 436, 142, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Elige Producto:");
		lblNewLabel_6.setBounds(30, 474, 117, 15);
		frame.getContentPane().add(lblNewLabel_6);
		
		JComboBox eligeProdcomboBox = new JComboBox();
	
		eligeProdcomboBox.setBounds(154, 469, 60, 24);
		frame.getContentPane().add(eligeProdcomboBox);
		
		// Llena el JComboBox con los IDs de los productos
		try {
		    Connection con = ConnectionSingleton.getConnection();
		    Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT idproducto FROM producto");
		    while (rs.next()) {
		        int idProducto = rs.getInt("idproducto");
		        eligeProdcomboBox.addItem(idProducto); // Agregar el ID al JComboBox
		        btnMostrar.doClick(); 
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		
		JButton eliminarbtnNewButton = new JButton("Eliminar");
		eliminarbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				try {
		            int idSeleccionado = (int) eligeProdcomboBox.getSelectedItem();

					con = ConnectionSingleton.getConnection();
				
				PreparedStatement dele_pstmt = con.prepareStatement("DELETE FROM producto WHERE idproducto = ?");
				dele_pstmt.setInt(1, idSeleccionado);
				int rowsDeleted =dele_pstmt.executeUpdate();
				dele_pstmt.close();
				JOptionPane.showMessageDialog(null, "Producto eliminado"); //Caso OK
				
				btnMostrar.doClick(); 
				
				}catch (SQLException ex) {
					
					ex.printStackTrace();

					 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
				
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	           
		        }

			}
		});
		eliminarbtnNewButton.setBounds(242, 469, 117, 25);
		frame.getContentPane().add(eliminarbtnNewButton);
		
		
		//ACTUALIZAR PRECIO
		JComboBox actualizarPreciocomboBox = new JComboBox();
		actualizarPreciocomboBox.setBounds(563, 57, 60, 22);
		frame.getContentPane().add(actualizarPreciocomboBox);
		
		// Llena el JComboBox con los IDs de los productos
				try {
				    Connection con = ConnectionSingleton.getConnection();
				    Statement stmt = con.createStatement();
				    ResultSet rs = stmt.executeQuery("SELECT idproducto FROM producto");
				    while (rs.next()) {
				        int idProducto = rs.getInt("idproducto");
				        actualizarPreciocomboBox.addItem(idProducto); // Agregar el ID al JComboBox
				        btnMostrar.doClick(); 
				    }
				} catch (SQLException e) {
				    e.printStackTrace();
				}
		
		nuevoPreciotextField = new JTextField();
		nuevoPreciotextField.setBounds(563, 104, 96, 20);
		frame.getContentPane().add(nuevoPreciotextField);
		nuevoPreciotextField.setColumns(10);
		
		JButton actualizarPreciobtn = new JButton("Actualizar precio");
		actualizarPreciobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    Connection con;
				try {
					con = ConnectionSingleton.getConnection();
					
					 int idSeleccionadoActualizarPrecio = (int) actualizarPreciocomboBox.getSelectedItem(); 
				
					PreparedStatement upd_pstmt = con.prepareStatement("UPDATE producto SET precio = ? WHERE idProducto = ?");
					upd_pstmt.setDouble(1, Double.parseDouble(nuevoPreciotextField.getText()));
					upd_pstmt.setInt(2, idSeleccionadoActualizarPrecio);
					int rowsUpdated = upd_pstmt.executeUpdate();
					upd_pstmt.close();
		
						JOptionPane.showMessageDialog(null, "Precio actualizado"); //Caso OK
						
						btnMostrar.doClick(); 
						
				}catch (SQLException ex) {
					
					ex.printStackTrace();

					 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
				
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	           
		        }


			}
		});
		actualizarPreciobtn.setBounds(710, 65, 130, 23);
		frame.getContentPane().add(actualizarPreciobtn);

		
		//INCREMENTAR STOCK
		JComboBox incrementarStockcomboBox = new JComboBox();
		incrementarStockcomboBox.setBounds(575, 242, 60, 22);
		frame.getContentPane().add(incrementarStockcomboBox);
		
		// Llena el JComboBox con los IDs de los productos
		try {
		    Connection con = ConnectionSingleton.getConnection();
		    Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT idproducto FROM producto");
		    while (rs.next()) {
		        int idProducto = rs.getInt("idproducto");
		        incrementarStockcomboBox.addItem(idProducto); // Agregar el ID al JComboBox
		        btnMostrar.doClick(); 
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		
		unidadesAdquiridastextField = new JTextField();
		unidadesAdquiridastextField.setBounds(573, 282, 96, 20);
		frame.getContentPane().add(unidadesAdquiridastextField);
		unidadesAdquiridastextField.setColumns(10);
		
		JButton actualizarStockbtn = new JButton("Actualizar stock");
		actualizarStockbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				try {
					con = ConnectionSingleton.getConnection();
					
					 int idSeleccionadoActualizarStock = (int) actualizarPreciocomboBox.getSelectedItem(); 
			         int cantidadAdquirida = Integer.parseInt(unidadesAdquiridastextField.getText());

			         // Obtener la cantidad actual de unidades del producto seleccionado
			            PreparedStatement pstmt = con.prepareStatement("SELECT unidades FROM producto WHERE idproducto = ?");
			            pstmt.setInt(1, idSeleccionadoActualizarStock);
			            ResultSet rs = pstmt.executeQuery();
			            
			            int cantidadActual = 0;
			            if (rs.next()) {
			                cantidadActual = rs.getInt("unidades");
			            }
			            
			            // Sumar la cantidad adquirida a la cantidad actual
			            int nuevaCantidad = cantidadActual + cantidadAdquirida;
					 
					PreparedStatement upd_pstmt = con.prepareStatement("UPDATE producto SET Unidades = ? WHERE idProducto = ?");
					upd_pstmt.setInt(1, nuevaCantidad);
					upd_pstmt.setInt(2, idSeleccionadoActualizarStock);
					int rowsUpdated = upd_pstmt.executeUpdate();
					upd_pstmt.close();
		
						JOptionPane.showMessageDialog(null, "Stock actualizado"); //Caso OK
						
						btnMostrar.doClick(); 
						
				}catch (SQLException ex) {
					
					ex.printStackTrace();

					 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
				
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	           
		        }

			}
		});
		actualizarStockbtn.setBounds(710, 242, 130, 23);
		frame.getContentPane().add(actualizarStockbtn);

		
		//GANANCIAS TOTALES		
				JLabel gananciasTotaleslbl = new JLabel("");
				gananciasTotaleslbl.setFont(new Font("Tahoma", Font.BOLD, 11));
				gananciasTotaleslbl.setForeground(Color.GREEN);
				gananciasTotaleslbl.setBounds(620, 530, 49, 14);
				frame.getContentPane().add(gananciasTotaleslbl);
		//VENDER
		JComboBox productoaVendercomboBox = new JComboBox();
		productoaVendercomboBox.setBounds(575, 432, 60, 22);
		frame.getContentPane().add(productoaVendercomboBox);
		// Llena el JComboBox con los IDs de los productos
				try {
				    Connection con = ConnectionSingleton.getConnection();
				    Statement stmt = con.createStatement();
				    ResultSet rs = stmt.executeQuery("SELECT idproducto FROM producto");
				    while (rs.next()) {
				        int idProducto = rs.getInt("idproducto");
				        productoaVendercomboBox.addItem(idProducto); // Agregar el ID al JComboBox
				        btnMostrar.doClick(); 
				       

				    }
				} catch (SQLException e) {
				    e.printStackTrace();
				}

		unidadesaVendertextField = new JTextField();
		unidadesaVendertextField.setBounds(573, 471, 96, 20);
		frame.getContentPane().add(unidadesaVendertextField);
		unidadesaVendertextField.setColumns(10);

		JButton venderbtn = new JButton("Vender");
		venderbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				try {
					con = ConnectionSingleton.getConnection();
					
					 int idSeleccionadoVender = (int) productoaVendercomboBox.getSelectedItem(); 
			         int cantidadVendida = Integer.parseInt(unidadesaVendertextField.getText());

			         // Obtener la cantidad actual de unidades del producto seleccionado
			            PreparedStatement pstmt = con.prepareStatement("SELECT Unidades FROM producto WHERE idproducto = ?");
			            pstmt.setInt(1, idSeleccionadoVender);
			            ResultSet rs = pstmt.executeQuery();
			            
			            int cantidadAhora = 0;
			            if (rs.next()) {
			            	cantidadAhora = rs.getInt("Unidades");
			            }
			            
			            if (cantidadAhora < cantidadVendida) {
			                JOptionPane.showMessageDialog(null, "No hay suficientes unidades disponibles para la venta");
			            } else {
			                // Realizar la venta 
			            int newCantidad = cantidadAhora - cantidadVendida;
					 
					PreparedStatement upd_pstmt = con.prepareStatement("UPDATE producto SET Unidades = ? WHERE idProducto = ?");
					upd_pstmt.setInt(1, newCantidad);
					upd_pstmt.setInt(2, idSeleccionadoVender);
					int rowsUpdated = upd_pstmt.executeUpdate();
					upd_pstmt.close();
		
						JOptionPane.showMessageDialog(null, "Venta realizada"); //Caso OK
						
						btnMostrar.doClick();
						
						double precioVenta = Double.parseDouble(unidadesaVendertextField.getText()) * getPrecioProducto(idSeleccionadoVender);
				        gananciasTotales += precioVenta;
				        gananciasTotaleslbl.setText(String.format("%.2f €", gananciasTotales));
			            }	
				}catch (SQLException ex) {
					
					ex.printStackTrace();

					 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
				
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	           
		        }

			}
		});
		venderbtn.setBounds(710, 436, 130, 23);
		frame.getContentPane().add(venderbtn);

		
		
		
		
		
		//LABELS
		JLabel lblNewLabel = new JLabel("Nuevo Producto:");
		lblNewLabel.setBounds(30, 216, 142, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Código:");
		lblNewLabel_1.setBounds(30, 243, 70, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Precio:");
		lblNewLabel_2.setBounds(30, 302, 70, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Nombre:");
		lblNewLabel_3.setBounds(30, 270, 70, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Unidades:");
		lblNewLabel_4.setBounds(30, 331, 81, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_7 = new JLabel("Actualizar precio:");
		lblNewLabel_7.setBounds(458, 17, 95, 14);
		frame.getContentPane().add(lblNewLabel_7);
					
		JLabel lblNewLabel_8 = new JLabel("Elige producto:");
		lblNewLabel_8.setBounds(458, 65, 98, 14);
		frame.getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Nuevo precio:");
		lblNewLabel_9.setBounds(458, 107, 85, 14);
		frame.getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Incrementar stock:");
		lblNewLabel_10.setBounds(458, 217, 98, 14);
		frame.getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Elige producto:");
		lblNewLabel_11.setBounds(458, 248, 98, 14);
		frame.getContentPane().add(lblNewLabel_11);
				
		JLabel lblNewLabel_12 = new JLabel("Unidades adquiridas:");
		lblNewLabel_12.setBounds(458, 285, 105, 14);
		frame.getContentPane().add(lblNewLabel_12);
				
		JLabel lblNewLabel_13 = new JLabel("Venta:");
		lblNewLabel_13.setBounds(458, 416, 49, 14);
		frame.getContentPane().add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("Producto a vender:");
		lblNewLabel_14.setBounds(458, 436, 98, 14);
		frame.getContentPane().add(lblNewLabel_14);
				
		JLabel lblNewLabel_15 = new JLabel("Unidades a vender:");
		lblNewLabel_15.setBounds(458, 474, 98, 14);
		frame.getContentPane().add(lblNewLabel_15);
				
		JLabel lblNewLabel_16 = new JLabel("Ganancias totales:");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_16.setForeground(Color.GREEN);
		lblNewLabel_16.setBounds(504, 530, 110, 14);
		frame.getContentPane().add(lblNewLabel_16);
		
		JLabel eurolbl = new JLabel("\u20AC");
		eurolbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		eurolbl.setForeground(Color.GREEN);
		eurolbl.setBounds(710, 530, 49, 14);
		frame.getContentPane().add(eurolbl);
		
	}
    }
