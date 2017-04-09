select * from stocks;

desc stockstrigger;

create or replace procedure updateStockTriggers(vsymbol stocks.symbol%type, vbuyPrice stockstrigger.buyprice%type, 
    vstoploss stockstrigger.stoploss%type, vprofitbook stockstrigger.profitbook%type, vstatus stockstrigger.status%type) 
is
begin
    
end;

show errors;