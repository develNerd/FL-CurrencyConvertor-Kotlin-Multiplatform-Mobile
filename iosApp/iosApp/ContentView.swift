import SwiftUI
import shared
import Foundation



extension Currency: Identifiable { }

struct ContentView: View {
    
 
    
    @State private var amount: String = "1"
    @State private var searchString: String = ""
    @StateObject var viewModel = ViewModel()
    @State private var showBaseBottomSheet = false
    @State private var showCurrencyBottomSheet = false
    @State private var currentCurrecy:Currency = Currency(code: "USD", usdRate: 1.00, name: "USD", conversion: 1.0)


  

    let columns = [
        GridItem(.flexible(minimum: 100)),
        GridItem(.flexible(minimum: 100)),
        GridItem(.flexible(minimum: 100))
    ]
    
	var body: some View {
        
        
        
        VStack{
            VStack(spacing: 10) {
                
                // Titile
                HStack { // << moved this up to ZStack
                    TitleView()
                       }
                
                // Amount Text field
                TextField(
                    "Enter Amount in \(viewModel.baseCurrency.code)",
                  text: $amount
                ).onChange(of: amount, perform: { newValue in
                    //print(newValue)
                    print(formatAmount(stringAmount: Double(newValue) ?? 0 ))
                    
                    
                    let moneyF = newValue.replacingOccurrences(of: ",", with: "", options: .literal, range: nil)
                    
                    var doubleAmount = 0.0
                    if(newValue.contains(".") && newValue.last == "."){
                        return
                    }
                    
                    if(!newValue.isEmpty){
                       doubleAmount = parseAmount(newValue: newValue)
                    }
                    amount = formatAmount(stringAmount: Double(moneyF) ?? 0 )
                    viewModel.calculateConversion(amount: doubleAmount)

                    
                    
                })
                    .padding(10)
                    .textInputAutocapitalization(.never)
                    .disableAutocorrection(true)
                    .textFieldStyle(.roundedBorder)
                    .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                    .lineSpacing(10)
                    .font(.system(size: 20, weight: .medium, design: .default))
                    .multilineTextAlignment(.trailing)


                // Bottom Sheet Botton
                HStack(alignment: .center){
                    Image(systemName: "chevron.down")
                    Text("\(viewModel.baseCurrency.name) (Current Currency)").font(.title3)
                }
                .frame(maxWidth: .infinity)
                .frame(maxHeight: 48)
                .foregroundColor(.white)
                .background(Color.redmi)
                .clipShape(RoundedRectangle(cornerRadius:5))
                .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                .onTapGesture {
                    showBaseBottomSheet.toggle()
                }.sheet(isPresented: $showBaseBottomSheet)
                {
                    //Bottom Sheet
                    VStack{
                        TextField(
                            "Search here",
                          text: $searchString
                        ).onChange(of: searchString, perform: { newValue in
                            viewModel.searchFilter(searchText: newValue)
                        })
                            .padding(10)
                            .textInputAutocapitalization(.never)
                            .disableAutocorrection(true)
                            .textFieldStyle(.roundedBorder)
                            .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                            .lineSpacing(10)
                            .font(.system(size: 20, weight: .medium, design: .default))
                        List(viewModel.bottomFilterCurrencies){currency in
                            FilterView(currency: currency){cur in
                                print("\(cur.name)")
                                viewModel.setBaseCurrency(currency: cur)
                                showBaseBottomSheet.toggle()
                                amount = "1"
                                viewModel.calculateConversion(amount: parseAmount(newValue: amount))
                            }.listStyle(.plain)
                        }.listStyle(.plain)
                    }
                    .presentationDetents([.fraction(0.60)])
                }
                
                Spacer()

                ScrollView{
                    LazyVGrid(columns: columns,spacing: 20){
                        ForEach($viewModel.currencies){$currency in
                            ConversionItem(currency: $currency){cur in
                                print("Clicked \(cur.code)")
                                currentCurrecy = cur
                                showCurrencyBottomSheet.toggle()
                            }.sheet(isPresented: $showCurrencyBottomSheet){
                                
                                CurrentBottomSheetView(currentCurrecy: $currentCurrecy){ cur in
                                    viewModel.setBaseCurrency(currency: cur)
                                    viewModel.calculateConversion(amount: parseAmount(newValue: amount))
                                    showCurrencyBottomSheet.toggle()
                                }
                                .presentationDetents([.fraction(0.15)])
                            }
                        }
                    }.padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                }

    
            }

            
        }.task {
            await viewModel.loadInit()
        }
        
	}
    
    
}


// Current Selected Currency Bottom Sheet
struct CurrentBottomSheetView : View {
    @Binding var currentCurrecy:Currency
    var onClick:(Currency) -> Void =  {_ in
    }
    var body: some View {
        VStack(alignment: .center,spacing: 30){
            let flag = "( " + countryFlag(countryCode: String(currentCurrecy.code.dropLast(1))) + " )"
            VStack(alignment: .leading,spacing: 5){
                Text(" \(flag) \(currentCurrecy.name )")
                
            }
            
            Button(action: {
                onClick(currentCurrecy)
            }, label: {
                Label("Set as Base Currency", systemImage: "pencil")
            })
        }
    }
    
}

// Filter Bottom Sheet
struct FilterView : View {
    var currency:Currency
    var onClick:(Currency) -> Void =  {_ in
        
    }
   init(currency: Currency,onClick:@escaping (Currency) -> Void) {
        self.currency = currency
       self.onClick = onClick
    }
    
    var body: some View {
        

        let flag = "( " + countryFlag(countryCode: String(currency.code.dropLast(1))) + " )"
        VStack(alignment: .leading,spacing: 5){
            Text(" \(flag) \(currency.name)")
        }.onTapGesture {
            self.onClick(self.currency)
        }
    }
}


struct ConversionItem : View {
    @Binding var currency:Currency
    var onClick:(Currency) -> Void =  {_ in
        
    }
    
    
    
    var body: some View {
        let flag = "( " + countryFlag(countryCode: String(currency.code.dropLast(1))) + " )"
        VStack {
            VStack(alignment: .center,spacing: 5){
                Text(currency.code).font(.title3)
                Text(formatConversion(stringAmount:String(currency.conversion)))
                Text(flag)
            }
        }
        .padding()
        .frame(maxWidth: .infinity)
        .overlay( /// apply a rounded border
            RoundedRectangle(cornerRadius: 10)
                .stroke(.cyan, lineWidth: 1)
        ).onTapGesture {
           onClick(currency)
        }
        
    }
}

struct TitleView:View {
    @State var counter: Int32 = 0
    var body: some View {
        VStack(spacing: 50) {
            Text("FL Currency Convertor").fontWeight(.bold)
        }
    }
}

/*struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		TitleView()
	}
} */

func countryFlag(countryCode: String) -> String {
  let base = 127397
  var tempScalarView = String.UnicodeScalarView()
  for i in countryCode.utf16 {
    if let scalar = UnicodeScalar(base + Int(i)) {
      tempScalarView.append(scalar)
    }
  }
  return String(tempScalarView)
}



func parseAmount(newValue:String) -> Double{
    var doubleAmount = 0.0
    if(!newValue.isEmpty){
        if(newValue.contains(".") && newValue.last == "."){
            
        }else {
            if let money = Double(newValue) {
                doubleAmount = money
            } else {
                if(newValue.contains(".")){
                    
                }else if(newValue.contains(",")) {
                    let moneyF = newValue.replacingOccurrences(of: ",", with: "", options: .literal, range: nil)

                    doubleAmount = Double(moneyF) ?? 0.0
                    print("moneyF \(doubleAmount)")
                }else {
                    doubleAmount = Double(newValue) ?? 0.0
                }
            }
        }
    }
    return doubleAmount
}
