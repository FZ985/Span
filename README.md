# Span
textview的span的使用




    
    Span.impl()   
     .append(Span.builder("哈哈哈"))    
     .append(Span.builder("嘿嘿嘿嘿嘿")    
              .backgroundColor(Color.GREEN))
     .append(Span.builder("呵呵呵呵呵呵呵呵")    
              .textColor(Color.RED))    
     .append(Span.builder("嘻嘻嘻嘻嘻嘻嘻嘻")   
              .textSize(30))  
     .append(Span.builder("哼哼哼哼哼")
              .textColor(Color.YELLOW)
              .underLine())
     .append(Span.builder("吼吼吼吼吼吼")
              .deleteLine())
     .append(Span.builder("点点点点")
              .click(new Span.OnClickSpanListener() {
                 @Override
                 public void onClick(String text, View widget) {
                  Toast.makeText(MainActivity.this, "点击了:" + text, Toast.LENGTH_SHORT).show();
                 }
             }))
     .append(Span.builder("啊啊啊啊啊啊啊"))
     .append(Span.builder("·待发布").roundSpan(new RoundSpan(Color.parseColor("#50ff0000"), 6, Paint.Style.FILL, 10, 10, 8f, 8f)))
     .into(main_tv);
